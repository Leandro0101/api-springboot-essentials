package com.curso.devdojo.handler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.curso.devdojo.error.ResourceNotFoundDetails;
import com.curso.devdojo.error.ResourceNotFoundException;
import com.curso.devdojo.error.ValidationErrorDetails;

@ControllerAdvice
public class RestExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rfnException){
		ResourceNotFoundDetails rfnDetails = ResourceNotFoundDetails.Builder.newBuilder
		().timestamp(new Date().getTime())
		.status(HttpStatus.NOT_FOUND.value())
		.title("Resource not found")
		.detail(rfnException.getMessage())
		.developerMessage(rfnException.getClass().getName())
		.build();
		return new ResponseEntity<>(rfnDetails, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException manvException){
		
		List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		ValidationErrorDetails rfnDetails = ValidationErrorDetails.Builder.newBuilder
		().timestamp(new Date().getTime())
		.status(HttpStatus.BAD_REQUEST.value())
		.title("Field Validation Error")
		.detail("Field Validation error")
		.developerMessage(manvException.getClass().getName())
		.field(fields)
		.fieldMessage(fieldMessages)
		.build();
		return new ResponseEntity<>(rfnDetails, HttpStatus.BAD_REQUEST);
	}
}
