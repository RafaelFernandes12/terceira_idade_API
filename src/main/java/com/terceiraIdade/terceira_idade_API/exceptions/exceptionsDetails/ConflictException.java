package com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends DataIntegrityViolationException {

    private static final long serialVersionUID = -3920038605448242712L;

	public ConflictException(String message) {
        super(message);
    }

}