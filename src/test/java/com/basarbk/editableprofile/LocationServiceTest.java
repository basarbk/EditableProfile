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

import com.basarbk.editableprofile.domain.GlobalLocations;
import com.basarbk.editableprofile.domain.dao.GlobalLocationsDao;
import com.basarbk.editableprofile.exception.LocationNotFoundException;
import com.basarbk.editableprofile.service.LocationService;

@RunWith(SpringRunner.class)
public class LocationServiceTest {
	
	LocationService locationService;
    
	@MockBean
    private GlobalLocationsDao locationDao;
	
	GlobalLocations basel = new GlobalLocations("47°34'N", "7°36'E", "Basel");
	GlobalLocations basra = new GlobalLocations("30°30'N", "47°49'E", "Basra");
	

	@Before
    public void setup(){
    	
		locationService = new LocationService(locationDao);
		List<GlobalLocations> bas = Arrays.asList(basel, basra);
    	Mockito.when(locationDao.findByCityStartingWithIgnoreCase("bas")).thenReturn(bas);
    	Mockito.when(locationDao.findByCityStartingWithIgnoreCase("base")).thenReturn(Arrays.asList(basel));
    }
    
    @Test
    public void getMatchingMultipleLocation() throws Exception {
    	List<GlobalLocations> locs = locationService.getMatchingLocations("bas"); 
    	assertThat(locs.size()).isEqualTo(2);
    }
    
    @Test(expected=LocationNotFoundException.class)
    public void getUnknownLocation() throws Exception {
    	locationService.getMatchingLocations("someunknownplacewhichisnotthere"); 
    }
    
    @Test
    public void getSingleMatchingLocation() throws Exception {
    	List<GlobalLocations> locs = locationService.getMatchingLocations("base"); 
    	assertThat(locs.size()).isEqualTo(1);
    	assertThat(locs.get(0).getCity()).isEqualTo(basel.getCity());
    }
    
}
