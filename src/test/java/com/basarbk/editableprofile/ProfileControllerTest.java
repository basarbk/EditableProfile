package com.basarbk.editableprofile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.basarbk.editableprofile.domain.Profile;
import com.basarbk.editableprofile.rest.ProfileController;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {
	
    @Autowired
    private MockMvc mvc;
    
    private String long257chars = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in ";
	
	@Test
	public void validationFailureEmptyObject() throws Exception {
		Profile profile = new Profile();
		mvc
		.perform(post("/api/profile").contentType(MediaType.APPLICATION_JSON_UTF8).content(json(profile)))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	public void validationFailuresForNullFields() throws Exception {
		Profile profile = new Profile();
		mvc
		.perform(post("/api/profile").contentType(MediaType.APPLICATION_JSON_UTF8).content(json(profile)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.displayName").exists())
		.andExpect(jsonPath("$.errors.realName").exists())
		.andExpect(jsonPath("$.errors.birthday").exists())
		.andExpect(jsonPath("$.errors.gender").exists())
		.andExpect(jsonPath("$.errors.marialStatus").exists())
		.andExpect(jsonPath("$.errors.location").exists());		
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
		mvc
		.perform(post("/api/profile").contentType(MediaType.APPLICATION_JSON_UTF8).content(json(profile)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.displayName").exists())
		.andExpect(jsonPath("$.errors.realName").exists())
		.andExpect(jsonPath("$.errors.occupation").exists())
		.andExpect(jsonPath("$.errors.aboutMe").exists());

	}
	
	@Test
	public void validationFailureForFutureDate() throws Exception {
		Profile profile = new Profile();
		Calendar c = Calendar.getInstance();
		c.set(2100, 1,1);
		profile.setBirthday(new Date(c.getTimeInMillis()));
		mvc
		.perform(post("/api/profile").contentType(MediaType.APPLICATION_JSON_UTF8).content(json(profile)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.birthday").exists());
	}
	
	@Test
	public void validationFailForLongDisplayName() throws Exception {
		Profile profile = new Profile();
		profile.setDisplayName(long257chars);
		mvc
		.perform(post("/api/profile").contentType(MediaType.APPLICATION_JSON_UTF8).content(json(profile)))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors.displayName").exists());
	}
	
    protected String json(Object o) throws IOException {
    	ObjectMapper mapper = new ObjectMapper();
    	return mapper.writeValueAsString(o);
    }

}
