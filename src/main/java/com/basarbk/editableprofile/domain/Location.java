package com.basarbk.editableprofile.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.basarbk.editableprofile.domain.vm.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Location implements Serializable {
	
	@Id @GeneratedValue
	private long id;
	
	@JsonView(View.Public.class)
	private String lat;
	
	@JsonView(View.Public.class)
	private String lon;
	
	@JsonView(View.Public.class)
	private String city;
	
	@OneToOne
	@JsonIgnore
	private Profile profile;

	public Location() {
		super();
	}

	public Location(String lat, String lon, String city) {
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

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	private static final long serialVersionUID = 4391985523873663024L;
}
