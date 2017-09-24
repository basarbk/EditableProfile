package com.basarbk.editableprofile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Profile Not Found")
public class ProfileNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -500799009478914860L;

}
