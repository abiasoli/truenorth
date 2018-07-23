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
	
	@RequestMapping(value = "/restaurant", method = RequestMethod.POST)
    public void addRestaurant(@RequestBody @Valid RestaurantDto restaurantDto) { 
		restaurantService.create(restaurantDto);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void deleteRestaurant(@PathVariable Long id) throws NotFoundException{
		restaurantService.delete(id);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<RestaurantDto> listRestaurants(@RequestParam(value="rating", required=false) Float rating) {
		return rating == null ? restaurantService.getAll() : restaurantService.getAllByRating(rating);
	} 
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public void updateRestaurant(@PathVariable @NotNull Long id, @RequestBody @Valid RestaurantDto restaurantDto) throws NotFoundException{
		restaurantService.update(id, restaurantDto);
	}
	
	@RequestMapping(value = "/restaurant/{id}/rating/{rating}", method = RequestMethod.PUT)
	public Float rateRestaurant(@PathVariable Long id, @PathVariable @Min(1) @Max(5) int rating) throws NotFoundException{
		return restaurantService.rate(id, rating);
	}
	
	@RequestMapping(value = "/restaurant/review", method = RequestMethod.POST)
	public Float addReview(@RequestBody @Valid ReviewDto reviewDto) throws NotFoundException{
		return restaurantService.addReview(reviewDto);
	}
}
