package com.basarbk.editableprofile.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basarbk.editableprofile.configuration.DataUtil;
import com.basarbk.editableprofile.service.LocationService;

@RestController
@RequestMapping("/api")
public class StaticDataController {
	
	LocationService locationService;
	
	public StaticDataController(LocationService locationService) {
		this.locationService = locationService;
	}

	@GetMapping("/static-data")
	public ResponseEntity<?> getStaticData(){
		Map<String, String[]> data = new HashMap<>();
		data.put("gender", DataUtil.gender);
		data.put("ethnicity", DataUtil.ethnicity);
		data.put("religion", DataUtil.religion);
		data.put("figure", DataUtil.figure);
		data.put("maritalStatus", DataUtil.maritalStatus);
		return ResponseEntity.ok(data);
	}
	
	@GetMapping("/locations/{loc:.+}")
	public ResponseEntity<?> getMatchingLocations(@PathVariable String loc){
		return ResponseEntity.ok(locationService.getMatchingLocations(loc));
	}

}
