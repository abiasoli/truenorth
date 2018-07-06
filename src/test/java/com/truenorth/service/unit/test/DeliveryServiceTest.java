package com.truenorth.service.unit.test;

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

import com.truenorth.domain.Location;
import com.truenorth.domain.Meal;
import com.truenorth.domain.Order;
import com.truenorth.domain.Restaurant;
import com.truenorth.dto.LocationDto;
import com.truenorth.dto.MealDto;
import com.truenorth.dto.NotificationDto;
import com.truenorth.dto.OrderDto;
import com.truenorth.exceptions.RestaurantException;
import com.truenorth.repository.DeliveryRepository;
import com.truenorth.repository.MealRepository;
import com.truenorth.service.DeliveryService;
import com.truenorth.service.GeoService;
import com.truenorth.service.OrderNotifier;
import com.truenorth.utils.DateUtils;

import javassist.NotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryServiceTest {

	private DeliveryService instance;
	@Mock
	private DeliveryRepository deliveryRepository;
	@Mock
	private MealRepository mealRepository;
	@Mock
	private GeoService geoService;
	@Mock
	private OrderNotifier orderNotifier;
	@Mock
	private DateUtils dateUtils;
	
	@Before
	public void setup(){
		this.instance = new DeliveryService(deliveryRepository, mealRepository, geoService, orderNotifier, dateUtils);
	}
	
	@Test
	public void whenCreate_withTwoMealsFromSameRestaurant_shouldCreateOrderAndSaveIt() throws NotFoundException{
		String address = "an address";
		LocationDto location = new LocationDto("-34.6067447", "-58.4202439");
		MealDto meal1 = new MealDto("Bife", "excelente bife", new BigDecimal(120));
		meal1.setId(1L);
		MealDto meal2 = new MealDto("Pastas", "sorrentinos", new BigDecimal(90));
		meal2.setId(2L);
		List<MealDto> meals = new ArrayList<>();
		meals.add(meal1);
		meals.add(meal2);
		OrderDto orderDto = new OrderDto(meals, address, location);
		Restaurant restaurant = new Restaurant();
		restaurant.setLocation(new Location(12131231.23, 1324324.81));
		restaurant.setId(1L);
		LocalDateTime eta = LocalDateTime.now();
		
		orderDto.getMeals().forEach(mealDto -> {
			Meal meal = new Meal(mealDto.getName(), mealDto.getDescription(), mealDto.getPrice(), restaurant);
			Optional<Meal> optional = Optional.of(meal);
			when(mealRepository.findById(Mockito.anyLong()))
			.thenReturn(optional);
		});
		
		when(geoService.calculateETA(Mockito.any(Location.class), Mockito.any(Location.class), Mockito.anyList())).thenReturn(eta);
		when(dateUtils.formatETA(Mockito.any(LocalDateTime.class))).thenReturn("25");
		
		instance.create(orderDto);
		
		verify(mealRepository, times(2)).findById(Mockito.anyLong());
		verify(geoService, times(1)).calculateETA(Mockito.any(Location.class), Mockito.any(Location.class), Mockito.anyList());
		verify(deliveryRepository, times(1)).save(Mockito.any(Order.class));
		verify(orderNotifier, times(1)).send(Mockito.any(NotificationDto.class));
		verify(dateUtils, times(1)).formatETA(Mockito.any(LocalDateTime.class));
	}
	
	@Test(expected = RestaurantException.class)
	public void whenCreate_withTwoMealsFromDifferentRestaurant_shouldthrowRestaurantException() throws NotFoundException{
		String address = "an address";
		LocationDto location = new LocationDto("-34.6067447", "-58.4202439");
		MealDto meal1 = new MealDto("Bife", "excelente bife", new BigDecimal(120));
		meal1.setId(1L);
		MealDto meal2 = new MealDto("Pastas", "sorrentinos", new BigDecimal(90));
		meal2.setId(2L);
		List<MealDto> meals = new ArrayList<>();
		meals.add(meal1);
		meals.add(meal2);
		OrderDto orderDto = new OrderDto(meals, address, location);
		
		for (MealDto mealDto : orderDto.getMeals()) {
			Restaurant restaurant = new Restaurant();
			restaurant.setLocation(new Location(12131231.23, 1324324.81));
			restaurant.setId(mealDto.getId());
			Meal meal = new Meal(mealDto.getName(), mealDto.getDescription(), mealDto.getPrice(), restaurant);
			Optional<Meal> optional = Optional.of(meal);
			when(mealRepository.findById(mealDto.getId()))
			.thenReturn(optional);
		}
		
		instance.create(orderDto);
		
		fail();
	}
	
	@Test(expected = NotFoundException.class)
	public void whenCreate_withNoMealToBeFound_shouldthrowNotFoundException() throws NotFoundException{
		
		String address = "an address";
		LocationDto location = new LocationDto("-34.6067447", "-58.4202439");
		MealDto meal1 = new MealDto("Bife", "excelente bife", new BigDecimal(120));
		meal1.setId(1L);
		MealDto meal2 = new MealDto("Pastas", "sorrentinos", new BigDecimal(90));
		meal2.setId(2L);
		List<MealDto> meals = new ArrayList<>();
		meals.add(meal1);
		meals.add(meal2);
		OrderDto orderDto = new OrderDto(meals, address, location);
		
		instance.create(orderDto);
		
		fail();
	}
}
