package com.basarbk.editableprofile.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.basarbk.editableprofile.domain.GlobalLocations;
import com.basarbk.editableprofile.domain.dao.GlobalLocationsDao;
import com.basarbk.editableprofile.exception.LocationNotFoundException;

@Service
public class GlobalLocationService {
	
	GlobalLocationsDao globalLocationsDao;

	public GlobalLocationService(GlobalLocationsDao globalLocationsDao) {
		this.globalLocationsDao = globalLocationsDao;
	}
	
	public List<GlobalLocations> getMatchingLocations(String loc){
		List<GlobalLocations> locations = globalLocationsDao.findByCityStartingWithIgnoreCase(loc);
		if(locations== null || locations.size()==0)
			throw new LocationNotFoundException();
		return locations;
	}
	
	

}
