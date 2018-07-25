package com.truenorth.service;

import static com.truenorth.utils.MessageUtils.USER_ERROR_RESTAURANNT_NOT_FOUND;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.truenorth.domain.Restaurant;
import com.truenorth.domain.Review;
import com.truenorth.dto.RestaurantDto;
import com.truenorth.dto.ReviewDto;
import com.truenorth.repository.RestaurantRepository;
import com.truenorth.repository.ReviewRepository;
import com.truenorth.utils.RestaurantConverter;

import javassist.NotFoundException;

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

	@Transactional
	public void create(RestaurantDto dto) {
		Restaurant restaurant = restaurantConverter.convertToDomain(dto);
		restaurantRepository.save(restaurant);
	}

	@Transactional
	public void delete(Long id) throws NotFoundException{
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);
		if(restaurant.isPresent()){
			restaurantRepository.delete(restaurant.get());
		}else{
			throw new NotFoundException(USER_ERROR_RESTAURANNT_NOT_FOUND);
		}
	}
	
	@Transactional(readOnly=true)
	public List<RestaurantDto> getAll() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		return toDto(restaurants);
	}

	@Transactional(readOnly=true)
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
	
	@Transactional
	public void update(Long id, RestaurantDto restaurantDto) throws NotFoundException{
		Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
		if(optionalRestaurant.isPresent()){
			Restaurant currentRestaurant = optionalRestaurant.get();
			Restaurant updatedRestaurant = restaurantConverter.convertToDomain(restaurantDto);
			restaurantConverter.setItemIds(updatedRestaurant, currentRestaurant);
			restaurantRepository.save(updatedRestaurant);
		}else{
			throw new NotFoundException(USER_ERROR_RESTAURANNT_NOT_FOUND);
		}
	}

	@Transactional
	public synchronized Float rate(Long id, int rating) throws NotFoundException {
		return getRatingAverage(id, rating, "anonymous", "");
	}
	
	@Transactional
	public synchronized Float addReview(ReviewDto dto) throws NotFoundException {
		return getRatingAverage(dto.getRestaurantId(), dto.getRating(), dto.getName(), dto.getReview());
	}
	
	private Float getRatingAverage(Long restaurantId, int rating, String name, String description) throws NotFoundException{
		Float newRating = new Float(rating);
		Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
		if(optionalRestaurant.isPresent()){
			Restaurant currentRestaurant = optionalRestaurant.get();
			Review review = new Review(name, description, rating, currentRestaurant);
			reviewRepository.save(review);
			newRating = currentRestaurant.calculateRating().floatValue();
			restaurantRepository.save(currentRestaurant);
		}else{
			throw new NotFoundException(USER_ERROR_RESTAURANNT_NOT_FOUND);
		}
		return newRating;
	}
	
	public RestaurantRepository getRepository() {
		return restaurantRepository;
	}

	@Transactional(readOnly=true)
	public RestaurantDto getById(Long restaurantId) throws NotFoundException {
		Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
		if(restaurant.isPresent()){
			return restaurantConverter.convertToDto(restaurant.get());
		}
		throw new NotFoundException(USER_ERROR_RESTAURANNT_NOT_FOUND);
	}
}
