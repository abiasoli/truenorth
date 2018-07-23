package com.truenorth.service.integration.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import com.truenorth.domain.Restaurant;
import com.truenorth.service.RestaurantService;
import com.truenorth.utils.TestScriptUtils;

import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RestaurantServiceTest {

	@Autowired
	private RestaurantService restaurantService;
	
	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.INSERT)
	public void delete_withValidRestaurantId_shouldDeleteRestaurant() throws NotFoundException{
		Long id = 1L;
		Optional<Restaurant> restaurant = restaurantService.getRepository().findById(id);
		assertTrue(restaurant.isPresent());
		
		restaurantService.delete(id);
		restaurant = restaurantService.getRepository().findById(id);
		assertFalse(restaurant.isPresent());
	}
	
}
