package com.terceiraIdade.terceira_idade_API.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.NotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private ErrorResponse errorResponse;

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> handleObjectNotFoundException(NotFoundException e,
			HttpServletRequest request) {
		return errorResponse.errorResponseBuilder(e, HttpStatus.NOT_FOUND, null, request);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
			MethodArgumentNotValidException e, HttpServletRequest request) {

		Map<String, String> errors = new HashMap<String, String>();

		e.getBindingResult().getFieldErrors().forEach(er -> {
			errors.put(er.getField(), er.getDefaultMessage());
		});

		return errorResponse.errorResponseBuilder(e, HttpStatus.BAD_REQUEST, errors, request);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
			DataIntegrityViolationException e, HttpServletRequest request) {

		String rootCauseMessage = ExceptionUtils.getRootCauseMessage(e);

		Map<String, String> errors = new HashMap<String, String>();
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if (rootCauseMessage.contains("course"))
			errors.put("curso", "Já existe um curso com o mesmo nome");
		else if (rootCauseMessage.contains("student"))
			errors.put("cpf", "CPF está em uso");
		else {
			errors.put("Servidor", "Um imprevisto ocorreu, tente novamente mais tarde!");
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return errorResponse.errorResponseBuilder(e, status, errors, request);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationExceptionException(
			ConstraintViolationException e, HttpServletRequest request) {

		Map<String, String> errors = new HashMap<>();

		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations) {
			errors.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return errorResponse.errorResponseBuilder(e, HttpStatus.BAD_REQUEST, errors, request);
	}
}
