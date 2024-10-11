package com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 5300307780941559775L;

	public BadRequestException(String message) {
		super(message);
	}
	
}
