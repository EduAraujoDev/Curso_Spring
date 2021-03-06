package br.com.alura.forum.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import br.com.alura.forum.exception.ResourceNotFoundException;

@ControllerAdvice
public class ExceptionHandleController {

	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request) {
		return new ResponseEntity<Object>("Resource not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<Object> handleBadCredentialsException(Exception exception, WebRequest request) {
		return new ResponseEntity<Object>("User or email invalid", new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
}