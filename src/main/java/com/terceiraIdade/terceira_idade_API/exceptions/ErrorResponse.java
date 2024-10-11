package com.terceiraIdade.terceira_idade_API.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ErrorResponse {

	private int status;
	private String message;
	private String stackTrace;
	private LocalDateTime timestamp;
	private Map<String, String> errors;
	private String path;

	@Value("${server.error.include-exception}")
	private static boolean printStackTrace;

	public ResponseEntity<ErrorResponse> errorResponseBuilder(Exception e, HttpStatus status,
			Map<String, String> errors, HttpServletRequest request) {
		String stackTrace = null;
		if (printStackTrace)
			stackTrace = ExceptionUtils.getStackTrace(e);

		return ResponseEntity.status(status)
				.body(ErrorResponse.builder().message(e.getMessage()).status(status.value())
						.timestamp(LocalDateTime.now()).errors(errors).path(request.getRequestURI())
						.stackTrace(stackTrace).build());
	}

	public ResponseEntity<ErrorResponse> errorResponseBuilder(String message, Exception e,
			HttpStatus status, Map<String, String> errors) {
		String stackTrace = null;
		if (printStackTrace)
			stackTrace = ExceptionUtils.getStackTrace(e);

		return ResponseEntity.status(status)
				.body(ErrorResponse.builder().message(message).status(status.value())
						.timestamp(LocalDateTime.now()).errors(errors).stackTrace(stackTrace)
						.build());
	}

}
