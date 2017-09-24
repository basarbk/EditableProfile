package com.basarbk.editableprofile.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.FORBIDDEN, reason="You are not allowed to modify profile")
public class ProfileAuthorizationException extends RuntimeException{

	private static final long serialVersionUID = -3823979181487450630L;

}
