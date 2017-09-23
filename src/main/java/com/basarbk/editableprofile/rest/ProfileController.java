package com.basarbk.editableprofile.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basarbk.editableprofile.domain.Profile;
import com.basarbk.editableprofile.domain.vm.ValidationError;

@RestController
@RequestMapping("/api")
public class ProfileController {
	
	
	@PostMapping("/profile")
	public ResponseEntity<?> createProfile(@Valid @RequestBody Profile profile, Errors errors) {
		if(errors.hasErrors()) {
			ValidationError validationError = new ValidationError();
			errors.getFieldErrors().stream().forEach(e -> validationError.addError(e.getField(), e.getDefaultMessage()));
			return ResponseEntity.badRequest().body(validationError);
		}
		
		return ResponseEntity.ok("done");
		
	}

}
