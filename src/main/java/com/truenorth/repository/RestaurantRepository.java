package com.truenorth.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truenorth.domain.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long>{

	List<Restaurant> findByRating(BigDecimal rating);
	
}
