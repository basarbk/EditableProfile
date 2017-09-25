package com.basarbk.editableprofile.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.basarbk.editableprofile.domain.Profile;
import com.basarbk.editableprofile.domain.dao.ProfileDao;
import com.basarbk.editableprofile.exception.ProfileNotFoundException;

@Service
public class ProfileService {
	
	ProfileDao profileDao;
	
	PhotoService photoService;
	
	LocationService locationService;
	
	public ProfileService(ProfileDao profileDao, PhotoService photoService, LocationService locationService) {
		this.profileDao = profileDao;
		this.photoService = photoService;
		this.locationService = locationService;
	}

	public List<Profile> getProfiles(){
		List<Profile> profiles = profileDao.findAll();
		if(profiles.size() == 0)
			throw new ProfileNotFoundException();
		return profiles;
	}
	
	public Profile getProfile(long id){
		Profile profile = profileDao.findOne(id);
		if(profile == null)
			throw new ProfileNotFoundException();
		return profile;
	}
	
	@Transactional
	public Profile updateProfile(long id, Profile profile){
		if(profile.getProfilePictureFile()!=null) {
			photoService.savePhoto(profile.getProfilePictureFile());
		}
		Profile inDB = getProfile(id);
		// since user is not allowed to edit height, making sure here to keep it same
		profile.setHeight(inDB.getHeight());
		locationService.deleteLocationOfPartner(id);
		profile.setId(id);
		profile.getLocation().setProfile(profile);
		profileDao.save(profile);
		return profile;
	}


	public Profile createProfile(Profile profile) {
		// implementation is intentionally skipped for profile creation, since this would be part of user creation step.
		// doing nothing here
		return null;
	}
}
