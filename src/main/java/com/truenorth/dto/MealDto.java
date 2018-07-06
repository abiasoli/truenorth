package com.truenorth.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public class MealDto {

	private Long id;
	private @NotBlank String name;
	private @NotBlank String description;
	private @NotBlank BigDecimal price;

	public MealDto(String name, String description, BigDecimal price) {
		this.name = name;
		this.description = description;
		this.price = price;
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

	@Override
	public String toString() {
		return "MealDto [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}
}
