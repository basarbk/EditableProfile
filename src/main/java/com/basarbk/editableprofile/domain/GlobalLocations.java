package com.basarbk.editableprofile.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class GlobalLocations implements Serializable {

	@Id @GeneratedValue
	private long id;

	private String lat;
	
	private String lon;
	
	private String city;
	
	public GlobalLocations() {
		super();
	}

	public GlobalLocations(String lat, String lon, String city) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.city = city;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	private static final long serialVersionUID = 7960328662777664476L;

	@Override
	public String toString() {
		return "GlobalLocations [lat=" + lat + ", lon=" + lon + ", city=" + city + "]";
	}

}
