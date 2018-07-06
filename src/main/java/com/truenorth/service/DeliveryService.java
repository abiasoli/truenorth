package com.truenorth.service;

import static com.truenorth.utils.MessageUtils.SYSTEM_ERROR_DIFFERENT_RESTAURANT_MEALS;
import static com.truenorth.utils.MessageUtils.SYSTEM_ERROR_MEAL_NOT_FOUND;
import static com.truenorth.utils.MessageUtils.USER_ERROR_DIFFERENT_RESTAURANT_MEALS;
import static com.truenorth.utils.MessageUtils.USER_ERROR_MEAL_NOT_FOUND;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truenorth.domain.Location;
import com.truenorth.domain.Meal;
import com.truenorth.domain.Order;
import com.truenorth.domain.Restaurant;
import com.truenorth.dto.NotificationDto;
import com.truenorth.dto.OrderDto;
import com.truenorth.exceptions.RestaurantException;
import com.truenorth.repository.DeliveryRepository;
import com.truenorth.repository.MealRepository;
import com.truenorth.utils.DateUtils;

import javassist.NotFoundException;

@Service
public class DeliveryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeliveryService.class);
	
	private DeliveryRepository deliveryRepository;
	private MealRepository mealRepository;
	private GeoService geoService;
	private OrderNotifier orderNotifier;
	private DateUtils dateUtils;
	
	@Autowired
	public DeliveryService(DeliveryRepository deliveryRepository, MealRepository mealRepository,
			GeoService geoService, OrderNotifier orderNotifier, DateUtils dateUtils){
		this.deliveryRepository = deliveryRepository;
		this.mealRepository = mealRepository;
		this.geoService = geoService;
		this.orderNotifier = orderNotifier;
		this.dateUtils = dateUtils;
	}
	
	public void create(OrderDto orderDto) throws NotFoundException {
		
		List<Meal> meals = new ArrayList<>();
		validateMeals(orderDto, meals);
		Restaurant restaurant = meals.stream().map(Meal::getRestaurant).findFirst().get();
		Location deliveryLocation = new Location(Double.valueOf(orderDto.getLocation().getLatitude()),
										 Double.valueOf(orderDto.getLocation().getLongitude()));
		
		LocalDateTime eta = geoService.calculateETA(restaurant.getLocation(), deliveryLocation, meals);
		Order order = new Order(meals, orderDto.getAddress(), restaurant, deliveryLocation, eta);
		deliveryRepository.save(order);
		orderDto.setEta(dateUtils.formatETA(eta));
		orderNotifier.send(new NotificationDto(orderDto, restaurant.getCommercialEmail(), restaurant.getCommercialName()));
	}

	private void validateMeals(OrderDto orderDto, List<Meal> meals) throws NotFoundException { // We validate all meals ordered are from the same restaurant
		
		try {
			orderDto.getMeals().forEach(mealDto -> {
				Meal mealEntity = mealRepository.findById(mealDto.getId()).get();
				mealDto.setPrice(mealEntity.getPrice());
				mealDto.setDescription(mealEntity.getDescription());
				mealDto.setName(mealEntity.getName());
				meals.add(mealEntity);
			});
		} catch (NoSuchElementException e) {
			LOGGER.error(SYSTEM_ERROR_MEAL_NOT_FOUND, e.getMessage());
			throw new NotFoundException(USER_ERROR_MEAL_NOT_FOUND);
		}
		
		Long count = meals.stream().map(Meal::getRestaurant).distinct().count();
		if(count > 1){
			LOGGER.error(SYSTEM_ERROR_DIFFERENT_RESTAURANT_MEALS);
			throw new RestaurantException(USER_ERROR_DIFFERENT_RESTAURANT_MEALS);
		}
	}
	
	
}
