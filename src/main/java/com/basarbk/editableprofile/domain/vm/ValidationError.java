package com.basarbk.editableprofile.domain.vm;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

public class ValidationError {
    
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, String> errors = new HashMap<>();

	public ValidationError() {
		super();
	}
	
	public void addError(String field, String message){
		errors.put(field, message);
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
}
