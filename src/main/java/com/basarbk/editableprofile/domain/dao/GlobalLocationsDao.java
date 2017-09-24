package com.basarbk.editableprofile.domain.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basarbk.editableprofile.domain.GlobalLocations;

public interface GlobalLocationsDao extends JpaRepository<GlobalLocations, Long>{
	
	List<GlobalLocations> findByCityStartingWithIgnoreCase(String city);

}
