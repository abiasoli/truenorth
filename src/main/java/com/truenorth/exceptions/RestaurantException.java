package com.truenorth.exceptions;

public class RestaurantException extends RuntimeException{

	private static final long serialVersionUID = 546294891861451268L;

	public RestaurantException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestaurantException(String message) {
		super(message);
	}

}
