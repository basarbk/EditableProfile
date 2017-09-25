package com.basarbk.editableprofile.configuration;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.basarbk.editableprofile.domain.Location;
import com.basarbk.editableprofile.domain.Profile;

/**
 * This is used for generating random profile and also providing static data for single select fields of profile
 * @author basarb
 *
 */
public class DataUtil {
	
	private static final String[] displayNames = {"Britney","Ortega","Clemons","Ray","Harrington","Mayo","Everett","Ann","Bernadette","Norma"};
	private static final String[] realNames = {"Rosie Le","Herman Shepard","Ball Craft","Bernice Pena","Molly Nguyen",
		      "Townsend Faulkner","Jacobson Caldwell","Logan Knowles","Kinney Blair","Beasley Dalton"};
	public static final String[] gender = {"Male","Female","Other"};
	public static final String[] ethnicity = {"White","South Asian","South East Asian","Mixed","Black","Arabic","Hispanic","Latino","Native American","Pacific Islander","Other"};
	public static final String[] religion = {"Agnostic","Atheist","Buddhist","Christian","Hindu","Islam","Jewish","Shinto","Sikh","Other"};
	public static final String[] figure = {"Slim","Normal","Athletic","A few extra kilos","More to love"};
	public static final String[] maritalStatus = {"Never Married","Divorced","Widower","Separated"};
	private static final Location[] locations = {new Location("41°01'N","28°57'E","Istanbul"),
			new Location("40°40'N", "73°56'W", "New York City"),
			new Location("41°54'N","12°30'E","Rome"),
			new Location("51°30'N","0°08'W","London"),
			new Location("34°03'N","118°15'W","Los Angeles"),
			new Location("48°51'N","2°21'E","Paris")};
	
	public static Profile getRandomProfile(){
		Random random = new Random();
		
		String occupation = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
		String aboutMe = "Aliquip excepteur officia irure dolor magna adipisicing laborum nostrud reprehenderit sint eiusmod enim esse. Pariatur elit nulla nisi adipisicing nisi reprehenderit dolore magna minim. Dolore pariatur occaecat quis incididunt qui ex. Lorem mollit sit non voluptate aute. In nulla sit esse laborum in mollit cillum quis dolor occaecat eu excepteur mollit.";
		int randomHeight = 150 + random.nextInt(40);
		
		Profile profile = new Profile();
		profile.setDisplayName(getRandomFromArray(displayNames));
		profile.setRealName(getRandomFromArray(realNames));
		profile.setProfilePicture("http://lorempixel.com/256/256/sports/"+random.nextInt(9));
		profile.setBirthday(getRandomBirthday());
		profile.setGender(getRandomFromArray(gender));
		profile.setEthnicity(getRandomFromArray(ethnicity));
		profile.setReligion(getRandomFromArray(religion));
		profile.setHeight(randomHeight);
		profile.setFigure(getRandomFromArray(figure));
		profile.setMaritalStatus(getRandomFromArray(maritalStatus));
		profile.setOccupation(occupation);
		profile.setAboutMe(aboutMe);

		// Randomly taking a location. since this is static object, multiple profile may hit same location
		// so copying it to a new object, to not to cause any persistence trouble with jpa
		Location location = getRandomFromArray(locations);
		Location locationCopy = new Location(location.getLat(), location.getLon(), location.getCity());
		locationCopy.setProfile(profile);
		profile.setLocation(locationCopy);
		return profile;
	}
	
	private static <T> T getRandomFromArray(T[] arr){
		Random random = new Random();
		int idx = random.nextInt(arr.length);
		return (T)(arr[idx]);
	}
	
	private static Date getRandomBirthday(){
		Calendar calendar = Calendar.getInstance();
		Random random = new Random();
		int year = 1950 + random.nextInt(65);
		int month = random.nextInt(12);
		int day = random.nextInt(29);
		calendar.set(year, month, day);
		return calendar.getTime();
	}
}
