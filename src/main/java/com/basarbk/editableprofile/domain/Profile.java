package com.basarbk.editableprofile.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.web.multipart.MultipartFile;

import com.basarbk.editableprofile.domain.vm.View;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
public class Profile implements Serializable{

	@Id @GeneratedValue
	@JsonView(View.Public.class)
	private long id;

	@NotNull(message = "{error.field.required}")
	@Size(min=2, max=256, message="{error.field.size.limit}")
	@SafeHtml(message="{error.field.safehtml}")
	@JsonView(View.Public.class)
	private String displayName;
	
	@NotNull(message = "{error.field.required}")
	@Size(min=2, max=256, message="{error.field.size.limit}")
	@SafeHtml(message="{error.field.safehtml}")
	@JsonView(View.Owner.class)
	private String realName;
	
	@JsonView(View.Public.class)
	private String profilePicture;
	
	@Temporal(TemporalType.DATE)
	@Past(message = "{error.field.birthday.past}")
	@NotNull(message = "{error.field.required}")
	@JsonView(View.Public.class)
	private Date birthday;
	
	@NotNull(message = "{error.field.required}")
	@JsonView(View.Public.class)
	private String gender;
	
	@JsonView(View.Public.class)
	private String ethnicity;
	
	@JsonView(View.Public.class)
	private String religion;
	
	@JsonView(View.Public.class)
	private int height;
	
	@JsonView(View.Public.class)
	private String figure;
	
	@NotNull(message = "{error.field.required}")
	@JsonView(View.Owner.class)
	private String maritalStatus;
	
	@Size(max=256, message="{error.field.size.maxlimit}")
	@SafeHtml(message="{error.field.safehtml}")
	@JsonView(View.Owner.class)
	private String occupation;
	
	@Size(max=5000, message="{error.field.size.maxlimit}")
	@SafeHtml(message="{error.field.safehtml}")
	@JsonView(View.Public.class)
	private String aboutMe;
	
	@NotNull(message = "{error.field.required}")
	@OneToOne(mappedBy="profile", cascade=CascadeType.ALL)
	@JsonView(View.Public.class)
	private Location location;
	
	@Transient
	@JsonIgnore
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

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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

	private static final long serialVersionUID = -5758197211026168135L;
	
}
