package com.basarbk.editableprofile;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.basarbk.editableprofile.configuration.DataUtil;
import com.basarbk.editableprofile.domain.Profile;
import com.basarbk.editableprofile.domain.vm.ValidationError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProfileControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
    
    private String long257chars = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in ";
	
    @Before
    public void setup() {
        BasicAuthorizationInterceptor basicAuth = new BasicAuthorizationInterceptor("testuser1", "testpassword");
        testRestTemplate.getRestTemplate().getInterceptors().add(basicAuth);
    }
    
	@Test
	public void validationFailureEmptyObject() throws Exception {
		Profile profile = new Profile();
		ResponseEntity<ValidationError> error = testRestTemplate.postForEntity("/api/profiles", profile, ValidationError.class);
		assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void validationFailuresForNullFields() throws Exception {
		Profile profile = new Profile();
		ResponseEntity<ValidationError> error = testRestTemplate.postForEntity("/api/profiles", profile, ValidationError.class);
		Map<String, String> errorMap = error.getBody().getErrors();
		assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(errorMap.containsKey("displayName")).isTrue();
		assertThat(errorMap.containsKey("realName")).isTrue();
		assertThat(errorMap.containsKey("birthday")).isTrue();
		assertThat(errorMap.containsKey("gender")).isTrue();
		assertThat(errorMap.containsKey("maritalStatus")).isTrue();
		assertThat(errorMap.containsKey("location")).isTrue();	
	}
	
	@Test
	public void validationFailuresForFieldsExceedingMaxValue() throws Exception {
		Profile profile = new Profile();
		profile.setDisplayName(long257chars);
		profile.setRealName(long257chars);
		profile.setOccupation(long257chars);
		
		StringBuilder longerThan5000chars = new StringBuilder();
		IntStream.rangeClosed(1, 20).forEach(i -> longerThan5000chars.append(long257chars));
		profile.setAboutMe(longerThan5000chars.toString());
		
		ResponseEntity<ValidationError> error = testRestTemplate.postForEntity("/api/profiles", profile, ValidationError.class);
		Map<String, String> errorMap = error.getBody().getErrors();
		assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(errorMap.containsKey("displayName")).isTrue();
		assertThat(errorMap.containsKey("realName")).isTrue();
		assertThat(errorMap.containsKey("occupation")).isTrue();
		assertThat(errorMap.containsKey("aboutMe")).isTrue();	

	}
	
	@Test
	public void validationFailureForFutureDate() throws Exception {
		Profile profile = new Profile();
		Calendar c = Calendar.getInstance();
		c.set(2100, 1,1);
		profile.setBirthday(new Date(c.getTimeInMillis()));
		
		ResponseEntity<ValidationError> error = testRestTemplate.postForEntity("/api/profiles", profile, ValidationError.class);
		Map<String, String> errorMap = error.getBody().getErrors();
		assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		assertThat(errorMap.containsKey("birthday")).isTrue();
	}
	
	@Test
	public void getProfiles() throws Exception {
		ResponseEntity<Object[]> profileList = testRestTemplate.getForEntity("/api/profiles", Object[].class);
		assertThat(profileList.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(profileList.getBody().length).isEqualTo(2);
	}
	
	@Test
	public void getOthersProfilePublicData() throws Exception {
		ResponseEntity<Profile> profileResponse = testRestTemplate.getForEntity("/api/profiles/2", Profile.class);
		assertThat(profileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		Profile profile = profileResponse.getBody();
		assertThat(profile.getRealName()).isNull();
		assertThat(profile.getMaritalStatus()).isNull();
		assertThat(profile.getOccupation()).isNull();
	}
	
	@Test
	public void getMyProfileOwnerData() throws Exception {
		ResponseEntity<Profile> profileResponse = testRestTemplate.getForEntity("/api/profiles/1", Profile.class);
		assertThat(profileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		Profile profile = profileResponse.getBody();
		assertThat(profile.getRealName()).isNotNull();
		assertThat(profile.getMaritalStatus()).isNotNull();
		assertThat(profile.getOccupation()).isNotNull();
	}
	
	@Test
	public void getUnknownProfile() throws Exception {
		ResponseEntity<Object> unknownProfileResponse = testRestTemplate.getForEntity("/api/profiles/1000", Object.class);
		assertThat(unknownProfileResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(unknownProfileResponse.getBody());
		assertThat(json.contains("Profile Not Found")).isTrue();
	}
	
	@Test
	public void updateProfileAuthFail() throws Exception {
		Profile profile = DataUtil.getRandomProfile();
		ResponseEntity<Profile> selfProfileResponse = testRestTemplate.getForEntity("/api/profiles/self", Profile.class);
		assertThat(selfProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile remote = selfProfileResponse.getBody();
		profile.setId(remote.getId());
		
		testRestTemplate.getRestTemplate().getInterceptors().clear();
		
		ResponseEntity<Object> error = testRestTemplate.exchange("/api/profiles/"+profile.getId(), HttpMethod.PUT, getRequestEntity(profile), Object.class);
		assertThat(error.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void updateProfileAuthFail2() throws Exception {
		Profile profile = DataUtil.getRandomProfile();
		ResponseEntity<Profile> selfProfileResponse = testRestTemplate.getForEntity("/api/profiles/self", Profile.class);
		assertThat(selfProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile remote = selfProfileResponse.getBody();
		profile.setId(remote.getId());
		
		ResponseEntity<Object> error = testRestTemplate.exchange("/api/profiles/10000", HttpMethod.PUT, getRequestEntity(profile), Object.class);
		assertThat(error.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}
	
	@Test
	public void updateProfileValidationFail() throws Exception {
		Profile profile = new Profile();
		ResponseEntity<Profile> selfProfileResponse = testRestTemplate.getForEntity("/api/profiles/self", Profile.class);
		assertThat(selfProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile remote = selfProfileResponse.getBody();
		profile.setId(remote.getId());
		
		ResponseEntity<ValidationError> error = testRestTemplate.exchange("/api/profiles/"+profile.getId(), HttpMethod.PUT, getRequestEntity(profile), ValidationError.class);
		assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Map<String, String> errorMap = error.getBody().getErrors();
		assertThat(errorMap.containsKey("displayName")).isTrue();
		assertThat(errorMap.containsKey("realName")).isTrue();
		assertThat(errorMap.containsKey("birthday")).isTrue();
		assertThat(errorMap.containsKey("gender")).isTrue();
		assertThat(errorMap.containsKey("maritalStatus")).isTrue();
		assertThat(errorMap.containsKey("location")).isTrue();	
	}
	
	@Test
	public void updateProfileSuccess() throws Exception {
		Profile profile = DataUtil.getRandomProfile();
		ResponseEntity<Profile> selfProfileResponse = testRestTemplate.getForEntity("/api/profiles/self", Profile.class);
		assertThat(selfProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile remote = selfProfileResponse.getBody();
		profile.setId(remote.getId());
		 
		ResponseEntity<Object> response = testRestTemplate.exchange("/api/profiles/"+profile.getId(), HttpMethod.PUT, getRequestEntity(profile), Object.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void updateProfileXSSValidationSuccess() throws Exception {
		ResponseEntity<Profile> selfProfileResponse = testRestTemplate.getForEntity("/api/profiles/self", Profile.class);
		assertThat(selfProfileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		Profile profile = selfProfileResponse.getBody();
		profile.setAboutMe("<script>alert(hey)</script>");
		
		ResponseEntity<ValidationError> error = testRestTemplate.exchange("/api/profiles/"+profile.getId(), HttpMethod.PUT, getRequestEntity(profile), ValidationError.class);
		assertThat(error.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		Map<String, String> errorMap = error.getBody().getErrors();
		assertThat(errorMap.containsKey("aboutMe")).isTrue();
		assertThat(errorMap.get("aboutMe")).isEqualTo("Html tags are not allowed");
	}
	
	private HttpEntity<String> getRequestEntity(Profile profile) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(profile);
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); 
		return new HttpEntity<String>(json, headers); 
	}

}
