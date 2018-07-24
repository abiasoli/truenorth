package com.truenorth.dto;

import java.util.List;

public class DeliveryGeoDto {

	private RestaurantDto restaurantDto;
	private LocationDto deliveryLocation;
	private List<MealDto> meals;
	
	public DeliveryGeoDto(RestaurantDto restaurantDto, LocationDto deliveryLocation, List<MealDto> meals) {
		this.restaurantDto = restaurantDto;
		this.deliveryLocation = deliveryLocation;
		this.meals = meals;
	}

	public LocationDto getDeliveryLocation() {
		return deliveryLocation;
	}

	public void setDeliveryLocation(LocationDto deliveryLocation) {
		this.deliveryLocation = deliveryLocation;
	}

	public List<MealDto> getMeals() {
		return meals;
	}

	public void setMeals(List<MealDto> meals) {
		this.meals = meals;
	}

	public RestaurantDto getRestaurantDto() {
		return restaurantDto;
	}

	public void setRestaurantDto(RestaurantDto restaurantDto) {
		this.restaurantDto = restaurantDto;
	}
	
}
