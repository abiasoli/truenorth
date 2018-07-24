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
import org.springframework.transaction.annotation.Transactional;

import com.truenorth.domain.Location;
import com.truenorth.domain.Meal;
import com.truenorth.domain.Order;
import com.truenorth.dto.DeliveryGeoDto;
import com.truenorth.dto.MealDto;
import com.truenorth.dto.OrderDto;
import com.truenorth.dto.RestaurantDto;
import com.truenorth.exceptions.RestaurantException;
import com.truenorth.repository.MealRepository;
import com.truenorth.repository.OrderRepository;

import javassist.NotFoundException;

@Service
public class OrderService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
	
	private OrderRepository orderRepository;
	private MealRepository mealRepository;
	private RestaurantService restaurantService;

	@Autowired
	public OrderService(OrderRepository orderRepository, MealRepository mealRepository,
			RestaurantService restaurantService) {
		this.orderRepository = orderRepository;
		this.mealRepository = mealRepository;
		this.restaurantService = restaurantService;
	}
	
	@Transactional
	public void createOrder(OrderDto orderDto, LocalDateTime eta) {
		List<Meal> meals = new ArrayList<>();
		Location deliveryLocation = new Location(orderDto.getLocation().getLatitude(), orderDto.getLocation().getLongitude());
		orderDto.getMeals().forEach(mealDto -> {
			Meal meal = mealRepository.findById(mealDto.getId()).get();
			meals.add(meal);
		});
		Meal anyMeal = meals.stream().findAny().get();
		Order order = new Order(meals, orderDto.getAddress(), anyMeal.getRestaurant(), deliveryLocation, eta);
		orderRepository.save(order);
	}
	
	@Transactional(readOnly=true)
	public DeliveryGeoDto validateOrder(OrderDto orderDto) throws NotFoundException{

		List<MealDto> mealsDto = validateMeals(orderDto.getMeals());
		Long restaurantId = mealsDto.stream().map(MealDto::getRestaurantId).findFirst().get();
		RestaurantDto restaurantDto = restaurantService.getById(restaurantId);
		return new DeliveryGeoDto(restaurantDto, orderDto.getLocation(), mealsDto);
	}
	
	private List<MealDto> validateMeals(List<MealDto> meals) throws NotFoundException { // We validate all meals ordered are from the same restaurant
		List<MealDto> mealsDto = new ArrayList<>();
		try {
			meals.forEach(mealDto -> {
				Meal mealEntity = mealRepository.findById(mealDto.getId()).get();
				mealDto.setPrice(mealEntity.getPrice());
				mealDto.setDescription(mealEntity.getDescription());
				mealDto.setName(mealEntity.getName());
				mealDto.setRestaurantId(mealEntity.getRestaurant().getId());
				mealDto.setCookTime(mealEntity.getCookTime());
				mealsDto.add(mealDto);
			});

		} catch (NoSuchElementException e) {
			LOGGER.error(SYSTEM_ERROR_MEAL_NOT_FOUND, e.getMessage());
			throw new NotFoundException(USER_ERROR_MEAL_NOT_FOUND);
		}
		
		Long count = meals.stream().map(MealDto::getRestaurantId).distinct().count();
		if(count > 1){
			LOGGER.error(SYSTEM_ERROR_DIFFERENT_RESTAURANT_MEALS);
			throw new RestaurantException(USER_ERROR_DIFFERENT_RESTAURANT_MEALS);
		}
		return mealsDto;
	}
	
}
