package com.truenorth.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;


public class MealDto {
	private Long id;
	private @NotBlank String name;
	private @NotBlank String description;
	private @NotBlank BigDecimal price;
	private Long restaurantId;
	private int cookTime;

	public MealDto(String name, String description, BigDecimal price, Long restaurantId) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.restaurantId = restaurantId;
	}
	
	public MealDto(Long id) {
		this.id = id;
	}
	
	public MealDto(){};
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	
	public int getCookTime() {
		return cookTime;
	}

	public void setCookTime(int cookTime) {
		this.cookTime = cookTime;
	}

	@Override
	public String toString() {
		return "MealDto [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", restaurantId=" + restaurantId + "]";
	}
	
}
