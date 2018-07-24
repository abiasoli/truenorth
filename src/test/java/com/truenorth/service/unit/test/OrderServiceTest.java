package com.truenorth.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.truenorth.domain.Meal;
import com.truenorth.domain.Order;
import com.truenorth.domain.Restaurant;
import com.truenorth.dto.DeliveryGeoDto;
import com.truenorth.dto.LocationDto;
import com.truenorth.dto.MealDto;
import com.truenorth.dto.OrderDto;
import com.truenorth.dto.RestaurantDto;
import com.truenorth.exceptions.RestaurantException;
import com.truenorth.repository.MealRepository;
import com.truenorth.repository.OrderRepository;
import com.truenorth.service.OrderService;
import com.truenorth.service.RestaurantService;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

	private OrderService instance;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private MealRepository mealRepository;
	@Mock
	private RestaurantService restaurantService;
	
	@Before
	public void setup(){
		this.instance = new OrderService(orderRepository, mealRepository, restaurantService);
	}
	
	@Test
	public void whenCreateOrder_withTwoMealsFromSameRestaurant_shouldCreateOrderAndSaveIt(){
		Long restaurantId = 1L;
		String address = "an address";
		LocationDto location = new LocationDto(-34.6067447, -58.4202439);
		MealDto meal1 = new MealDto("Bife", "excelente bife", new BigDecimal(120), restaurantId);
		meal1.setId(1L);
		MealDto meal2 = new MealDto("Pastas", "sorrentinos", new BigDecimal(90), restaurantId);
		meal2.setId(2L);
		List<MealDto> meals = new ArrayList<>();
		meals.add(meal1);
		meals.add(meal2);
		OrderDto orderDto = new OrderDto(meals, address, location);
		LocalDateTime eta = LocalDateTime.now();
		Restaurant restaurant = new Restaurant();
		
		orderDto.getMeals().forEach(mealDto -> {
			Meal meal = new Meal(mealDto.getName(), mealDto.getDescription(), mealDto.getPrice(), restaurant);
			Optional<Meal> optional = Optional.of(meal);
			when(mealRepository.findById(Mockito.anyLong())).thenReturn(optional);
		});
		
		instance.createOrder(orderDto, eta);
		
		verify(mealRepository, times(2)).findById(Mockito.anyLong());
		verify(orderRepository, times(1)).save(Mockito.any(Order.class));
	}
	
	@Test
	public void whenValidateOrder_withExistingMealsFromSameRestaurant_shouldReturnDeliveryGeoDto() throws NotFoundException{
		String address = "an address";
		LocationDto location = new LocationDto(-34.6067447, -58.4202439);
		MealDto meal1 = new MealDto("Bife", "excelente bife", new BigDecimal(120), 1L);
		meal1.setId(1L);
		MealDto meal2 = new MealDto("Pastas", "sorrentinos", new BigDecimal(90), 1L);
		meal2.setId(2L);
		List<MealDto> meals = new ArrayList<>();
		meals.add(meal1);
		meals.add(meal2);
		OrderDto orderDto = new OrderDto(meals, address, location);
		RestaurantDto restaurantDto = new RestaurantDto();
		when(restaurantService.getById(Mockito.anyLong())).thenReturn(restaurantDto);
		orderDto.getMeals().forEach(mealDto -> {
			Restaurant restaurant = new Restaurant();
			restaurant.setId(1L);
			Meal meal = new Meal(mealDto.getName(), mealDto.getDescription(), mealDto.getPrice(), restaurant);
			Optional<Meal> optional = Optional.of(meal);
			when(mealRepository.findById(Mockito.anyLong())).thenReturn(optional);
		});
		
		DeliveryGeoDto geoDto = instance.validateOrder(orderDto);
		
		assertNotNull(geoDto);
		assertNotNull(geoDto.getRestaurantDto());
		assertEquals(orderDto.getLocation(), geoDto.getDeliveryLocation());
		for (int i = 0; i < meals.size(); i++) {
			assertEquals(meals.get(i).getId(), geoDto.getMeals().get(i).getId());
			assertEquals(meals.get(i).getRestaurantId(), geoDto.getMeals().get(i).getRestaurantId());
			assertEquals(meals.get(i).getName(), geoDto.getMeals().get(i).getName());
			assertEquals(meals.get(i).getDescription(), geoDto.getMeals().get(i).getDescription());
			assertEquals(meals.get(i).getPrice(), geoDto.getMeals().get(i).getPrice());
		}
		verify(mealRepository, times(2)).findById(Mockito.anyLong());
		verify(restaurantService, times(1)).getById(Mockito.anyLong());
	}
	
	@Test(expected = RestaurantException.class)
	public void whenValidateOrder_withTwoMealsFromDifferentRestaurant_shouldthrowRestaurantException() throws NotFoundException{
		String address = "an address";
		LocationDto location = new LocationDto(-34.6067447, -58.4202439);
		MealDto meal1 = new MealDto("Bife", "excelente bife", new BigDecimal(120), 1L);
		meal1.setId(1L);
		MealDto meal2 = new MealDto("Pastas", "sorrentinos", new BigDecimal(90), 2L);
		meal2.setId(2L);
		List<MealDto> meals = new ArrayList<>();
		meals.add(meal1);
		meals.add(meal2);
		OrderDto orderDto = new OrderDto(meals, address, location);
		
		for (MealDto mealDto : orderDto.getMeals()) {
			Restaurant restaurant = new Restaurant();
			restaurant.setId(mealDto.getRestaurantId());
			Meal meal = new Meal(mealDto.getName(), mealDto.getDescription(), mealDto.getPrice(), restaurant);
			Optional<Meal> optional = Optional.of(meal);
			when(mealRepository.findById(mealDto.getId())).thenReturn(optional);
		}
		
		instance.validateOrder(orderDto);
		
		fail();
	}
	
	@Test(expected = NotFoundException.class)
	public void whenValidateOrder_withNoMealToBeFound_shouldthrowNotFoundException() throws NotFoundException{
		Long restaurantId = 1L;
		String address = "an address";
		LocationDto location = new LocationDto(-34.6067447, -58.4202439);
		MealDto meal1 = new MealDto("Bife", "excelente bife", new BigDecimal(120), restaurantId);
		meal1.setId(1L);
		MealDto meal2 = new MealDto("Pastas", "sorrentinos", new BigDecimal(90), restaurantId);
		meal2.setId(2L);
		List<MealDto> meals = new ArrayList<>();
		meals.add(meal1);
		meals.add(meal2);
		OrderDto orderDto = new OrderDto(meals, address, location);
		
		instance.validateOrder(orderDto);
		
		fail();
	}
}
