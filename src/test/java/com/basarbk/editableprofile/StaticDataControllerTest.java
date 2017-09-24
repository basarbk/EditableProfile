package com.basarbk.editableprofile;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@SuppressWarnings("rawtypes")
public class StaticDataControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
    
    @Before
    public void setup() {
        BasicAuthorizationInterceptor basicAuth = new BasicAuthorizationInterceptor("testuser1", "testpassword");
        testRestTemplate.getRestTemplate().getInterceptors().add(basicAuth);
    }
    
	@Test
	public void getStaticData() throws Exception {
		ResponseEntity<HashMap> result = testRestTemplate.getForEntity("/api/static-data", HashMap.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		HashMap map = result.getBody();
		assertThat(map.keySet().size()).isEqualTo(5);
		assertThat(map.keySet().size()).isEqualTo(5);
		assertThat(map.keySet().contains("figure")).isTrue();
		assertThat(map.keySet().contains("gender")).isTrue();
		assertThat(map.keySet().contains("ethnicity")).isTrue();
		assertThat(map.keySet().contains("maritalStatus")).isTrue();
		assertThat(map.keySet().contains("religion")).isTrue();
	}
	
	@Test
	public void getLocationSuccess() throws Exception {
		ResponseEntity<List> result = testRestTemplate.getForEntity("/api/locations/las", List.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(result.getBody().size()).isNotEqualTo(0);
	}
	
	@Test
	public void getLocationFail() throws Exception {
		ResponseEntity<Object> result = testRestTemplate.getForEntity("/api/locations/aaabbbcccc", Object.class);
		assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

}
