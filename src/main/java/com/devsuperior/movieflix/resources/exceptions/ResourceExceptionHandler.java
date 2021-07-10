package com.devsuperior.movieflix.resources.exceptions;

import com.devsuperior.movieflix.services.exception.DatabaseException;
import com.devsuperior.movieflix.services.exception.ForbiddenException;
import com.devsuperior.movieflix.services.exception.ResourceNotFoundException;
import com.devsuperior.movieflix.services.exception.UnauthorazedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;


@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
		var status = HttpStatus.NOT_FOUND;
		var error = getStandardError(ex, status, request, "Resource not found");
		return ResponseEntity.status(status).body(error);
	}	
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> database(DatabaseException ex, HttpServletRequest request) {
		var status = HttpStatus.CONFLICT;
		var error = getStandardError(ex, status, request, "Database exception");
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(ForbiddenException.class)
	public ResponseEntity<OAuthCustomError> forbidden(ForbiddenException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.FORBIDDEN;
		var error = new OAuthCustomError("Forbidden", e.getMessage());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(UnauthorazedException.class)
	public ResponseEntity<OAuthCustomError> unauthorized(UnauthorazedException e, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		var error = new OAuthCustomError("Unauthorized", e.getMessage());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException ex, HttpServletRequest request) {
		var status = HttpStatus.UNPROCESSABLE_ENTITY;
		var error = getValidationError(ex, status, request, "Validation exception");
		return ResponseEntity.status(status).body(error);
	}

	private StandardError getStandardError(Exception ex, HttpStatus status,
										   HttpServletRequest request, String errorMsg) {
		var error = new StandardError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(errorMsg);
		error.setMessage(ex.getMessage());
		error.setPath(request.getRequestURI());
		return error;
	}

	private ValidationError getValidationError(MethodArgumentNotValidException ex, HttpStatus status,
											   HttpServletRequest request, String errorMsg) {
		var error = new ValidationError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError(errorMsg);
		error.setMessage(ex.getMessage());
		error.setPath(request.getRequestURI());
		for (FieldError field : ex.getBindingResult().getFieldErrors()) {
			error.addError(field.getField(), field.getDefaultMessage());
		}
		return error;
	}
}
