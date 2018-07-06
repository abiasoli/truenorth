package com.truenorth.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;

public class OrderDto {

	private List<MealDto> meals;
	@NotBlank
	private String address;
	private LocationDto location;
	private String eta;

	public OrderDto(List<MealDto> meals, String address, LocationDto location) {
		this.meals = meals;
		this.address = address;
		this.location = location;
	}
	
	public OrderDto(){}

	public BigDecimal getTotalCost() {
		return meals.stream().map(MealDto::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<MealDto> getMeals() {
		return meals;
	}

	public void setMeals(List<MealDto> meals) {
		this.meals = meals;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocationDto getLocation() {
		return location;
	}

	public void setLocation(LocationDto location) {
		this.location = location;
	}
	
	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	@Override
	public String toString() {
		return "OrderDto [meals=" + meals + ", address=" + address + ", location=" + location + ", eta=" + eta + "]";
	}

}
