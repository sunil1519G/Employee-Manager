package com.sunil.employeeManger.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		Map<String,String> errorsMessage = new HashMap<>();
		Map<String,Object> finalMessage = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errorsMessage.put(fieldName, message);
		});
		
		finalMessage.put("message", ex.getMessage());
		finalMessage.put("Http Status", HttpStatus.BAD_REQUEST);
		finalMessage.put("errors", errorsMessage);
		
		return new ResponseEntity<Object>(errorsMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex) {

		Map<String,String> errors = new HashMap<>();
		errors.put("message", ex.getMessage());
		errors.put("exception", ex.toString());
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({UserNotFoundException.class, EmptyResultDataAccessException.class})
	protected ResponseEntity<Object> handleUserNotFoundException(Exception ex) {

		Map<String,String> errors = new HashMap<>();
		errors.put("message", ex.getMessage());
		errors.put("exception", ex.toString());
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
	
}
