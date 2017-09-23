package com.basarbk.editableprofile.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class Profile implements Serializable{

	@Id
	private long id;

	@NotNull(message = "{error.field.required}")
	@Size(max=256, message="{error.field.size.limit}")
	private String displayName;
	
	@NotNull(message = "{error.field.required}")
	@Size(max=256, message="{error.field.size.limit}")
	private String realName;
	
	private String profilePicture;
	
	@Temporal(TemporalType.DATE)
	@Past(message = "{error.field.birthday.past}")
	@NotNull(message = "{error.field.required}")
	private Date birthday;
	
	@NotNull(message = "{error.field.required}")
	private String gender;
	
	private String ethnicity;
	
	private String religion;
	
	private int height;
	
	private String figure;
	
	@NotNull(message = "{error.field.required}")
	private String marialStatus;
	
	@Size(max=256, message="{error.field.size.limit}")
	private String occupation;
	
	@Size(max=5000, message="{error.field.size.limit}")
	private String aboutMe;
	
	@NotNull(message = "{error.field.required}")
	@OneToOne(mappedBy="profile")
	private Location location;
	
	@OneToOne(mappedBy="profile")
	private User user;
	
	@Transient
	private MultipartFile profilePictureFile;
	
	
	public Profile() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getFigure() {
		return figure;
	}

	public void setFigure(String figure) {
		this.figure = figure;
	}

	public String getMarialStatus() {
		return marialStatus;
	}

	public void setMarialStatus(String marialStatus) {
		this.marialStatus = marialStatus;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public MultipartFile getProfilePictureFile() {
		return profilePictureFile;
	}

	public void setProfilePictureFile(MultipartFile profilePictureFile) {
		this.profilePictureFile = profilePictureFile;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private static final long serialVersionUID = -5758197211026168135L;
	
}
