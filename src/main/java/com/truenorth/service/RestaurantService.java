package com.truenorth.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public Float rate(Long id, Integer rating) {
		BigDecimal newRating = BigDecimal.ZERO;
		Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
			if(optionalRestaurant.isPresent()){
				Restaurant currentRestaurant = optionalRestaurant.get();
				if(currentRestaurant.getRating().compareTo(BigDecimal.ZERO) != 0){
					Review review = new Review("annonymous", "", rating, currentRestaurant);
					reviewRepository.save(review);
					newRating = currentRestaurant.calculateRating();
					restaurantRepository.save(currentRestaurant);
				}
			}
		return newRating.floatValue();
	}

	public RestaurantRepository getRepository() {
		return restaurantRepository;
	}

	public Float addReview(ReviewDto dto) {
		Float rating = 0F;
		Long restaurantId = dto.getRestaurantId();
		Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
			if(optionalRestaurant.isPresent()){
				Restaurant restaurant = optionalRestaurant.get();
				Review review = new Review(dto.getName(), dto.getReview(), dto.getRating(), restaurant);
				reviewRepository.save(review);
				rating = restaurant.calculateRating().floatValue();
				restaurantRepository.save(restaurant);
			}
		return rating;
	}
	
	
}
