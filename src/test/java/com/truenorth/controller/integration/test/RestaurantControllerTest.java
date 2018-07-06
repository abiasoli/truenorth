package com.truenorth.controller.integration.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.truenorth.dto.RestaurantDto;
import com.truenorth.exceptions.ExceptionResponse;
import com.truenorth.utils.TestScriptUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.DROP)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.CREATE)
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.INSERT)
public class RestaurantControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;
	@Autowired
	private ObjectMapper mapper;
	
	@Before
	public void setup(){
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
	}
	
	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.INSERT)
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = TestScriptUtils.DELETE)
	public void whenFindAllRestaurants_allRestaurantsShouldBeReturned() {
		
		String url = "http://localhost:" + port + "/api/truenorth/restaurants";
		
		RestaurantDto[] restaurants = restTemplate.getForObject(url, RestaurantDto[].class);
        
		assertNotNull(restaurants);
	    assertTrue(restaurants.length > 0);
	}
	
	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.INSERT)
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = TestScriptUtils.DELETE)
	public void whenRateRestaurants_withValidRate_averageRatingShouldBeReturned() {
		
		Float expectedAverage = 4.25F;
		String id = "1", newRate = "5";
		String url = "http://localhost:" + port + "/api/truenorth/restaurants/restaurant/{id}/rating/{rate}";
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id);
		pathVariables.put("rate", newRate);
		 
		ResponseEntity<Float> response = restTemplate.exchange(url, HttpMethod.PUT, null, Float.class, pathVariables);
        Float newRating = response.getBody();
		assertNotNull(newRating);
		assertTrue(newRating > 0);
		assertTrue(newRating <= 5);
		assertEquals(expectedAverage, newRating);
	}
	
	@Test
	@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = TestScriptUtils.INSERT)
	@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = TestScriptUtils.DELETE)
	public void whenRateRestaurants_withInvalidRate_shouldThrowExceptionWithCustomMessage() {
		
		String id = "1", newRate = "6";
		String url = "http://localhost:" + port + "/api/truenorth/restaurants/restaurant/{id}/rating/{rate}";
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id);
		pathVariables.put("rate", newRate);

		ResponseEntity<ExceptionResponse> response = restTemplate.exchange(url, HttpMethod.PUT, null, ExceptionResponse.class, pathVariables);
		assertNotNull(response);
		ExceptionResponse exception = response.getBody();
		assertEquals("rateRestaurant.rating: must be less than or equal to 5", exception.getErrorMessage());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getErrorCode());
	
	}
}
