package com.truenorth.service.unit.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.google.maps.model.LatLng;
import com.truenorth.connector.GeoConnector;
import com.truenorth.dto.LocationDto;
import com.truenorth.dto.MealDto;
import com.truenorth.dto.RestaurantDto;
import com.truenorth.service.GeoService;
import com.truenorth.utils.DateUtils;

@RunWith(MockitoJUnitRunner.class)
public class GeoServiceTest {

	private GeoService instance;
	@Mock
	private GeoConnector connector;
	@Mock
	private DateUtils dateUtils; 
	
	@Before
	public void setup(){
		this.instance = new GeoService(connector, dateUtils);
	}
	
	@Test
	public void whenCalculateETA_withLocationsForTwoMeals_shouldReturnExpectedETA(){
		DateTime dateTime = new DateTime(2018, 1, 1, 10, 25, 0);
		LocalDateTime expectedEta = LocalDateTime.of(2018, 1, 1, 10, 55, 0);
		LocationDto from = new LocationDto(45.454545, 69.478787);
		LocationDto to = new LocationDto(35.412479, 53.232421);
		RestaurantDto restaurant = new RestaurantDto();
		MealDto meal1 = new MealDto("Bife", "excelente bife", new BigDecimal(120), restaurant.getId());
		MealDto meal2 = new MealDto("Pastas", "sorrentinos", new BigDecimal(90), restaurant.getId());
		List<MealDto> meals = new ArrayList<MealDto>();
		meals.add(meal1);
		meals.add(meal2);
		when(dateUtils.now()).thenReturn(dateTime);
		when(connector.getTransitTime(Mockito.any(LatLng.class), Mockito.any(LatLng.class), Mockito.any(ReadableInstant.class)))
			.thenReturn(600);
		when(dateUtils.toLocalDateTime(Mockito.any(DateTime.class))).thenReturn(expectedEta);
		
		LocalDateTime eta = instance.calculateETA(from, to, meals);
		
		assertEquals(expectedEta, eta);
		verify(dateUtils, times(1)).now();
		verify(dateUtils, times(1)).toLocalDateTime(Mockito.any(DateTime.class));
		verify(connector, times(1)).getTransitTime(Mockito.any(LatLng.class), Mockito.any(LatLng.class), Mockito.any(ReadableInstant.class));
	}
}
