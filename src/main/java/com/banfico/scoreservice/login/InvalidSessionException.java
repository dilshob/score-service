package com.banfico.scoreservice.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid Session Key")
public class InvalidSessionException extends RuntimeException{
	private static final long serialVersionUID = 1L;
}
