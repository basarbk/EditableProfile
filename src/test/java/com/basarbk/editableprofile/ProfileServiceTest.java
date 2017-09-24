package com.basarbk.editableprofile;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.basarbk.editableprofile.configuration.DataUtil;
import com.basarbk.editableprofile.domain.Profile;
import com.basarbk.editableprofile.domain.dao.ProfileDao;
import com.basarbk.editableprofile.exception.ProfileNotFoundException;
import com.basarbk.editableprofile.service.PhotoService;
import com.basarbk.editableprofile.service.ProfileService;

@RunWith(SpringRunner.class)
public class ProfileServiceTest {
	
	ProfileService profileService;
    
	@MockBean
    private ProfileDao profileDao;
	
	@MockBean
    private PhotoService photoService;
	
	Profile profile;
	
    @Before
    public void setup(){
    	
    	profile = DataUtil.getRandomProfile();
    	
    	profileService = new ProfileService(profileDao, photoService);
    	Mockito.when(profileDao.findOne(1L)).thenReturn(profile);
    }
    
    @Test
    public void getProfile() throws Exception {
    	Profile p = profileService.getProfile(1);
    	assertThat(p.getDisplayName()).isEqualTo(profile.getDisplayName());
    }
    
    @Test(expected=ProfileNotFoundException.class)
    public void getUnknownProfile() throws Exception {
    	profileService.getProfile(100);
    }
    
    @Test(expected=ProfileNotFoundException.class)
    public void getProfilesEmpty() throws Exception {
    	profileService.getProfiles();
    }
    
    @Test
    public void getProfiles() throws Exception {
    	Mockito.when(profileDao.findAll()).thenReturn(Arrays.asList(profile));
    	List<Profile> profiles = profileService.getProfiles();
    	assertThat(profiles.size()).isEqualTo(1);
    }
}
