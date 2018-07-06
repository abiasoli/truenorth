package com.truenorth.dto;

import java.io.Serializable;


public class NotificationDto implements Serializable {

	private static final long serialVersionUID = -4791947125960438048L;

	private OrderDto order;
	private String restaurantEmail;
	private String restaurantName;

	public NotificationDto(OrderDto order, String restaurantEmail, String restaurantName) {
		this.order = order;
		this.restaurantEmail = restaurantEmail;
		this.restaurantName = restaurantName;
	}

	public NotificationDto(){}
	
	public OrderDto getOrder() {
		return order;
	}

	public String getRestaurantEmail() {
		return restaurantEmail;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	@Override
	public String toString() {
		return "NotificationDto [order=" + order + ", restaurantEmail=" + restaurantEmail + ", restaurantName="
				+ restaurantName + "]";
	}
	
}
