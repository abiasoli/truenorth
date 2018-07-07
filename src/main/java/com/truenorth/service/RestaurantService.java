package com.truenorth.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truenorth.domain.Restaurant;
import com.truenorth.domain.Review;
import com.truenorth.dto.RestaurantDto;
import com.truenorth.dto.ReviewDto;
import com.truenorth.repository.RestaurantRepository;
import com.truenorth.repository.ReviewRepository;
import com.truenorth.utils.RestaurantConverter;

@Service
public class RestaurantService {
	
	private RestaurantRepository restaurantRepository;
	private RestaurantConverter restaurantConverter;
	private ReviewRepository reviewRepository;
	
	@Autowired
	public RestaurantService(RestaurantRepository repository, RestaurantConverter restaurantUtils,
			ReviewRepository reviewRepository) {
		this.restaurantRepository = repository;
		this.restaurantConverter = restaurantUtils;
		this.reviewRepository = reviewRepository;
	}

	public void create(RestaurantDto dto) {
		Restaurant restaurant = restaurantConverter.convertToDomain(dto);
		restaurantRepository.save(restaurant);
	}

	public void delete(Long id) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if(restaurant.isPresent()){
			restaurantRepository.delete(restaurant.get());
		}
	}
	
	public List<RestaurantDto> getAll() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return toDto(restaurants);
	}

	public List<RestaurantDto> getAllByRating(Float rating) {
		List<Restaurant> restaurants = restaurantRepository.findByRating(new BigDecimal(rating).setScale(2, RoundingMode.HALF_EVEN));
		return toDto(restaurants);
	}
	
	private List<RestaurantDto> toDto(List<Restaurant> restaurants){
		List<RestaurantDto> allRestaurants = new ArrayList<>();
		for (Restaurant restaurant : restaurants) {
			allRestaurants.add(restaurantConverter.convertToDto(restaurant));
		}
		return allRestaurants;
	}
	
	public void update(Long id, RestaurantDto restaurantDto) {
		Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
		if(optionalRestaurant.isPresent()){
			Restaurant currentRestaurant = optionalRestaurant.get();
			Restaurant updatedRestaurant = restaurantConverter.convertToDomain(restaurantDto);
			restaurantConverter.setItemIds(updatedRestaurant, currentRestaurant);
			restaurantRepository.save(updatedRestaurant);
		}
	}

	@Transactional(rollbackOn=Exception.class)
	public synchronized Float rate(Long id, int rating) {
		return getRatingAverage(id, rating, "annonymous", "");
	}
	
	@Transactional(rollbackOn=Exception.class)
	public synchronized Float addReview(ReviewDto dto) {
		return getRatingAverage(dto.getRestaurantId(), dto.getRating(), dto.getName(), dto.getReview());
	}
	
	private Float getRatingAverage(Long restaurantId, int rating, String name, String description){
		Float newRating = new Float(rating);
		Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
		if(optionalRestaurant.isPresent()){
			Restaurant currentRestaurant = optionalRestaurant.get();
			Review review = new Review(name, description, rating, currentRestaurant);
			reviewRepository.save(review);
			newRating = currentRestaurant.calculateRating().floatValue();
			restaurantRepository.save(currentRestaurant);
		}
		return newRating;
	}
	
	public RestaurantRepository getRepository() {
		return restaurantRepository;
	}
}
