package com.truenorth.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

public class RestaurantDto {

	private Long id;
	private @URL String logo;
	private @NotBlank String legalName;
	private @NotBlank String commercialName;
	@Min(value=1)
	@Max(value=5)
	private float rating;
	private @Valid List<ReviewDto> reviews;
	private List<MealDto> meals;
	private @Email String commercialEmail;
	private @NotBlank String adminNumber;
	private @NotBlank String address;
	private LocationDto location;
	
	public RestaurantDto(){};
	
	public RestaurantDto(Long id, String logo, String commercialName, String legalName, float rating, List<ReviewDto> reviews, List<MealDto> meals,
			String commercialEmail, String adminNumber, String address, LocationDto location) {
		this.id = id;
		this.logo = logo;
		this.commercialName = commercialName;
		this.legalName = legalName;
		this.rating = rating;
		this.reviews = reviews;
		this.meals = meals;
		this.commercialEmail = commercialEmail;
		this.adminNumber = adminNumber;
		this.address = address;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}
	
	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public List<ReviewDto> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDto> reviews) {
		this.reviews = reviews;
	}

	public List<MealDto> getMeals() {
		return meals;
	}

	public void setMeals(List<MealDto> meals) {
		this.meals = meals;
	}

	public String getCommercialEmail() {
		return commercialEmail;
	}

	public void setCommercialEmail(String commercialEmail) {
		this.commercialEmail = commercialEmail;
	}

	public String getAdminNumber() {
		return adminNumber;
	}

	public void setAdminNumber(String adminNumber) {
		this.adminNumber = adminNumber;
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
	
	
}
