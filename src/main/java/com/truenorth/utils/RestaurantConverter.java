package com.truenorth.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.truenorth.domain.Item;
import com.truenorth.domain.Restaurant;
import com.truenorth.dto.RestaurantDto;

import ma.glasnost.orika.MapperFacade;

@Component
public class RestaurantConverter {

	private MapperFacade mapperFacade;
	
	@Autowired
	public RestaurantConverter(MapperFacade mapperFacade){
		this.mapperFacade = mapperFacade;
	}
	
	public RestaurantDto convertToDto(Restaurant restaurant){
		return mapperFacade.map(restaurant, RestaurantDto.class);
	}
	
	public Restaurant convertToDomain(RestaurantDto dto) {
		Restaurant restaurant = mapperFacade.map(dto, Restaurant.class);
		setItemRestaurant(restaurant.getReviews(), restaurant);
		setItemRestaurant(restaurant.getMeals(), restaurant);
		return restaurant;
	}
	
	public void setItemIds(Restaurant updatedRestaurant, Restaurant currentRestaurant) {
		updatedRestaurant.setId(currentRestaurant.getId());
		updatedRestaurant.getLocation().setId(currentRestaurant.getId());
	}
	
	private void setItemRestaurant(List<? extends Item> items, Restaurant restaurant){
		items.forEach(item -> item.setRestaurant(restaurant));
	}

}
