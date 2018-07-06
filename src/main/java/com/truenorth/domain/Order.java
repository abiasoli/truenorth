package com.truenorth.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal totalCost;
	
	@ManyToMany
	private List<Meal> meals;
	
	@Column(nullable=false)
	private String address;
	
	@OneToOne
	private Restaurant restaurant;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Location to;
	
	@Column(nullable=false)
	private LocalDateTime eta;
	
	public Order(List<Meal> meals, String address, Restaurant restaurant, Location to,
			LocalDateTime eta) {
		this.meals = meals;
		this.totalCost = calculateTotalCost();
		this.address = address;
		this.restaurant = restaurant;
		this.to = to;
		this.eta = eta;
	}

	public Order() {}
	
	private BigDecimal calculateTotalCost() {
		return meals.stream().map(Meal::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Location getTo() {
		return to;
	}

	public void setTo(Location to) {
		this.to = to;
	}

	public LocalDateTime getEta() {
		return eta;
	}

	public void setEta(LocalDateTime eta) {
		this.eta = eta;
	}

	public Long getId() {
		return id;
	}
	
}
