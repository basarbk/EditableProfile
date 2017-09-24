package com.basarbk.editableprofile.service;

import org.springframework.stereotype.Service;

import com.basarbk.editableprofile.domain.dao.LocationDao;

@Service
public class LocationService {
	
	LocationDao locationDao;

	public LocationService(LocationDao locationDao) {
		this.locationDao = locationDao;
	}
	
	public void deleteLocationOfPartner(long partnerid){
		locationDao.deleteByProfileId(partnerid);
	}

}
