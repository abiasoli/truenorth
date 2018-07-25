package com.truenorth.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.truenorth.dto.RestaurantDto;
import com.truenorth.dto.ReviewDto;
import com.truenorth.service.RestaurantService;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@RequestMapping("/api/truenorth/restaurants")
@Validated
public class RestaurantController {

	private RestaurantService restaurantService;
	
	@Autowired
	public RestaurantController(RestaurantService restaurantService){
		this.restaurantService = restaurantService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value="Post Restaurant")
    public void addRestaurant(@RequestBody @Valid RestaurantDto restaurantDto) { 
		restaurantService.create(restaurantDto);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value="Delete Restaurant")
	public void deleteRestaurant(@PathVariable Long id) throws NotFoundException{
		restaurantService.delete(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value="Get all Restaurants / Filter by Rating", response = RestaurantDto.class)
	public List<RestaurantDto> listRestaurants(@RequestParam(value="rating", required=false) Float rating) {
		return rating == null ? restaurantService.getAll() : restaurantService.getAllByRating(rating);
	} 
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ApiOperation(value="Update Restaurant by Id")
	public void updateRestaurant(@PathVariable @NotNull Long id, @RequestBody @Valid RestaurantDto restaurantDto) throws NotFoundException{
		restaurantService.update(id, restaurantDto);
	}
	
	@RequestMapping(value = "/{id}/rating/{rating}", method = RequestMethod.PUT)
	@ApiOperation(value="Rate Restaurant anonymously by Id")
	public Float rateRestaurant(@PathVariable Long id, @PathVariable @Min(1) @Max(5) int rating) throws NotFoundException{
		return restaurantService.rate(id, rating);
	}
	
	@RequestMapping(value = "/review", method = RequestMethod.POST)
	@ApiOperation(value="Post Review", response=Float.class)
	public Float addReview(@RequestBody @Valid ReviewDto reviewDto) throws NotFoundException{
		return restaurantService.addReview(reviewDto);
	}
}
