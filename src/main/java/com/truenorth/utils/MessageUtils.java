package com.truenorth.utils;

public class MessageUtils {

	// ERROR MESSAGES
	public static final String SYSTEM_ERROR = "An error has occurred. Message: {}";
	public static final String SYSTEM_ERROR_DURATION_NOT_FOUND = "No duration could be found!";
	public static final String SYSTEM_ERROR_MEAL_NOT_FOUND = "There was an error while finding meal by id. Message: {}";
	public static final String SYSTEM_ERROR_DIFFERENT_RESTAURANT_MEALS = "The order contained meals from different restaurants!";
	public static final String USER_ERROR_DURATION_NOT_FOUND = "Sorry, ETA for delivery could not be determined.";
	public static final String USER_ERROR_MEAL_NOT_FOUND = "Sorry, the meal chosen could not be found!";
	public static final String USER_ERROR_DIFFERENT_RESTAURANT_MEALS = "The order can only have meals from only one restaurant!";
	
	// INFO MESSAGES
	public static final String SYSTEM_INFO_SENDING_SMS = "Sending SMS message to customer: {}";
	public static final String SYSTEM_INFO_SENDING_EMAIL = "Sending email to customer: {}";
	public static final String SYSTEM_INFO_ORDER_NOTIFIED = "Order sent to topic {}";
	public static final String SYSTEM_INFO_ORDER_RECEIVED = "Order received in topic {}";
}
