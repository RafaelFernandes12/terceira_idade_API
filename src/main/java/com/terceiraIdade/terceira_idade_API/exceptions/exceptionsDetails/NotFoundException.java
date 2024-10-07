package com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = -8744666130652385361L;

	public NotFoundException(String message) {
		super(message);
	}

}
