package com.truenorth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.truenorth.domain.Meal;

public interface MealRepository extends JpaRepository<Meal, Long>{
	
}
