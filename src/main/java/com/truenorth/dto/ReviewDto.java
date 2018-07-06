package com.truenorth.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ReviewDto {

	private Long id;
	private @NotBlank String name;
	private String review;
	@Min(value=1)
	@Max(value=5)
	private int rating;
	private Long restaurantId;
	
	public ReviewDto(String name, String review, int rating) {
		this.name = name;
		this.review = review;
		this.rating = rating;
	}
	
	public ReviewDto(String name, String review, int rating, Long restaurantId) {
		this.name = name;
		this.review = review;
		this.rating = rating;
		this.restaurantId = restaurantId;
	}
	
	public ReviewDto() {
	}

	public String getName() {
		return name;
	}
	
	public String getReview() {
		return review;
	}
	
	public int getRating() {
		return rating;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
