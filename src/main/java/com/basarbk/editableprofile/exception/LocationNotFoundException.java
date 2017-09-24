package com.basarbk.editableprofile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Location not found")
public class LocationNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3404262373702585154L;

}
