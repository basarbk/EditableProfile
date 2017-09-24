package com.basarbk.editableprofile.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basarbk.editableprofile.domain.Profile;
import com.basarbk.editableprofile.domain.User;
import com.basarbk.editableprofile.domain.vm.ValidationError;
import com.basarbk.editableprofile.domain.vm.View;
import com.basarbk.editableprofile.exception.ProfileAuthorizationException;
import com.basarbk.editableprofile.exception.ProfileNotFoundException;
import com.basarbk.editableprofile.service.ProfileService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/api")
public class ProfileController {
	
	ProfileService profileService;
	
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@GetMapping("/profiles/self")
	public ResponseEntity<?> getMyProfile(){
		User user = getLoggedInUser();
		if(user == null)
			throw new ProfileNotFoundException();
		
		return ResponseEntity.ok(profileService.getProfile(user.getProfile().getId()));
	}
	
	@GetMapping("/profiles")
	@JsonView(View.Public.class)
	public ResponseEntity<?> getAllProfile(){
		return ResponseEntity.ok(profileService.getProfiles());
	}
	
	@GetMapping("/profiles/{id:[0-9]+}")
	public ResponseEntity<?> getProfile(@PathVariable long id){
		User user = getLoggedInUser();
		if(user!=null && user.getProfile() != null && user.getProfile().getId() == id){
			Profile profile = profileService.getProfile(id);
			MappingJacksonValue jsonValue = new MappingJacksonValue(profile);
			jsonValue.setSerializationView(View.Owner.class);
			return ResponseEntity.ok(jsonValue);
		}
		
		Profile profile = profileService.getProfile(id);
		MappingJacksonValue jsonValue = new MappingJacksonValue(profile);
		jsonValue.setSerializationView(View.Public.class);
		return ResponseEntity.ok(jsonValue);
	}
	
	@PostMapping("/profiles")
	public ResponseEntity<?> createProfile(@Valid @RequestBody Profile profile, Errors errors) {
		if(getLoggedInUser() == null)
			throw new ProfileAuthorizationException();
		
		if(errors.hasErrors())
			return ResponseEntity.badRequest().body(buildErrorBody(errors));
		
		Profile savedProfile = profileService.createProfile(profile);
		return ResponseEntity.ok(savedProfile);
		
	}
	
	@PutMapping("/profiles/{id:[0-9]+}")
	public ResponseEntity<?> upateProfile(@PathVariable long id, @Valid @RequestBody Profile profile, Errors errors) {
		User user = getLoggedInUser();

		if(user == null)
			throw new ProfileAuthorizationException();
		
		if(user.getProfile() == null || user.getProfile().getId() != id)
			throw new ProfileAuthorizationException();
		
		if(errors.hasErrors())
			return ResponseEntity.badRequest().body(buildErrorBody(errors));
		
		Profile profileUpdated = profileService.updateProfile(id, profile);
		
		return ResponseEntity.ok(profileUpdated);
		
	}
	
	private ValidationError buildErrorBody(Errors errors){
		ValidationError validationError = new ValidationError();
		errors.getFieldErrors().stream().forEach(e -> validationError.addError(e.getField(), e.getDefaultMessage()));
		return validationError;
	}
	
	private User getLoggedInUser(){
		Object userPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userPrincipal==null || !(userPrincipal instanceof User))
			return null;
		
		return (User) userPrincipal;
	}
	

}
