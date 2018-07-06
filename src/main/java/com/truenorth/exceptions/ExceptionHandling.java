package com.truenorth.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javassist.NotFoundException;

@ControllerAdvice
public class ExceptionHandling {

	public static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandling.class);
	
	@ExceptionHandler(RestaurantException.class)
    public ResponseEntity<ExceptionResponse> invalidInput(RestaurantException ex) {
        
		LOGGER.error(ex.getMessage(), ex);
		
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.BAD_REQUEST.name());
        response.setErrorMessage(ex.getMessage());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> invalidInput(NotFoundException ex) {
        
		LOGGER.error(ex.getMessage(), ex);
		
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.NOT_FOUND.name());
        response.setErrorMessage(ex.getMessage());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> invalidInput(Exception ex) {
        
		LOGGER.error(ex.getMessage(), ex);
		
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.name());
        response.setErrorMessage(ex.getMessage());
        
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
