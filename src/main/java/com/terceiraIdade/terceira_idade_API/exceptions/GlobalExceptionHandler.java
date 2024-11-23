package com.terceiraIdade.terceira_idade_API.exceptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.BadRequestException;
import com.terceiraIdade.terceira_idade_API.exceptions.exceptionsDetails.ForbiddenException;
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

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception e,
			HttpServletRequest request) {
		return errorResponse.errorResponseBuilder(e, HttpStatus.INTERNAL_SERVER_ERROR, null,
				request);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException e,
			HttpServletRequest request) {
		return errorResponse.errorResponseBuilder(e, HttpStatus.FORBIDDEN, null, request);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e,
			HttpServletRequest request) {
		return errorResponse.errorResponseBuilder(e, HttpStatus.BAD_REQUEST, null, request);
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
		return errorResponse.errorResponseBuilder(e, HttpStatus.CONFLICT, null, request);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationExceptionException(
			ConstraintViolationException e, HttpServletRequest request) {

		Map<String, String> errors = new HashMap<>();

		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations) {
			errors.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return errorResponse.errorResponseBuilder(e, HttpStatus.UNPROCESSABLE_ENTITY, errors,
				request);
	}
}
