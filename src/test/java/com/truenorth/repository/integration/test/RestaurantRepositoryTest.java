package com.truenorth.repository.integration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
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
import com.truenorth.repository.RestaurantRepository;
import com.truenorth.utils.TestScriptUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class RestaurantRepositoryTest {

	@Autowired
	private RestaurantRepository instance;
	
	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.INSERT)
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = TestScriptUtils.DELETE)
	public void findById_withExistintRestaurantId_shouldReturnRestaurant(){
		
		Optional<Restaurant> restaurant = instance.findById(1L);
		
		assertTrue(restaurant.isPresent());
	}
	
	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.INSERT)
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = TestScriptUtils.DELETE)
	public void findAllByRating_withExistintRating_shouldReturnRestaurantWithRatingChosen(){
		
		BigDecimal ratingChosen = new BigDecimal("5.00");
		List<Restaurant> restaurants = instance.findByRating(ratingChosen);
		
		assertTrue(!restaurants.isEmpty());
		assertEquals(ratingChosen, restaurants.stream().findFirst().get().getRating());
	}
}
