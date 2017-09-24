package com.basarbk.editableprofile.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.basarbk.editableprofile.domain.GlobalLocations;
import com.basarbk.editableprofile.domain.Profile;
import com.basarbk.editableprofile.domain.User;
import com.basarbk.editableprofile.domain.dao.GlobalLocationsDao;
import com.basarbk.editableprofile.domain.dao.ProfileDao;
import com.basarbk.editableprofile.domain.dao.UserDao;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class InitData {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ProfileDao profileDao;
	
	@Autowired
	GlobalLocationsDao globalLocationsDao;
	
	public void loadCityData() throws JsonParseException, JsonMappingException, IOException{
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream("cities.json");
		ObjectMapper mapper = new ObjectMapper();
		CitiesWrapper wrapper = mapper.readValue(is, CitiesWrapper.class);
		globalLocationsDao.save(wrapper.getCities());
	}
	
	@Bean
	@org.springframework.context.annotation.Profile("!test")
	CommandLineRunner initDemoProfiles(){
		return (args) -> {
				Profile user1Profile = DataUtil.getRandomProfile();
				Profile user2Profile = DataUtil.getRandomProfile();
				
				profileDao.save(user1Profile);
				profileDao.save(user2Profile);
				
				User user1 = new User();
				user1.setUsername("user1");
				user1.setPassword("password");
				user1.setProfile(user1Profile);
				userDao.save(user1);
				
				User user2 = new User();
				user2.setUsername("user2");
				user2.setPassword("password");
				user2.setProfile(user2Profile);
				userDao.save(user2);
				
				loadCityData();
			};
	}
	
	@Bean
	@org.springframework.context.annotation.Profile("test")
	CommandLineRunner initTestProfiles(){
		return (args) -> {
			Profile user1Profile = DataUtil.getRandomProfile();
			Profile user2Profile = DataUtil.getRandomProfile();

			profileDao.save(user1Profile);
			profileDao.save(user2Profile);
			
			User user1 = new User();
			user1.setUsername("testuser1");
			user1.setPassword("testpassword");
			user1.setProfile(user1Profile);
			userDao.save(user1);
			
			User user2 = new User();
			user2.setUsername("testuser2");
			user2.setPassword("testpassword");
			user2.setProfile(user2Profile);
	        userDao.save(user2);
	        
	        loadCityData();
		};
	}
}

class CitiesWrapper {
	
	private List<GlobalLocations> cities = new ArrayList<>();

	public CitiesWrapper() {
		super();
	}

	public List<GlobalLocations> getCities() {
		return cities;
	}

	public void setCities(List<GlobalLocations> cities) {
		this.cities = cities;
	}
	
	
}