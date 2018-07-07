package com.truenorth.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="restaurants")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String logo;
	private BigDecimal rating;
	
	@Column(nullable=false)
	private String legalName;

	@Column(nullable=false)
	private String commercialName;
	
	@Column(nullable=false)
	private String commercialEmail;
	
	@Column(nullable=false)
	private String adminNumber;
	
	@Column(nullable=false)
	private String address;
	
	@OneToOne(cascade= CascadeType.ALL)
	private Location location;
	
	@OneToMany(mappedBy="restaurant", cascade= CascadeType.ALL)
	private List<Review> reviews;
	
	@OneToMany(mappedBy="restaurant", cascade= CascadeType.ALL)
	private List<Meal> meals;
	
	@OneToMany(mappedBy="restaurant", cascade= CascadeType.ALL)
	private List<Order> orders;
	
	public Restaurant(Long id, String logo, String commercialName, String legalName, BigDecimal rating,
			String commercialEmail, String adminNumber, String address, Location location) {
		this.id = id;
		this.logo = logo;
		this.commercialName = commercialName;
		this.legalName = legalName;
		this.rating = rating;
		this.commercialEmail = commercialEmail;
		this.adminNumber = adminNumber;
		this.address = address;
		this.location = location;
	}

	public Restaurant() {
	}

	public BigDecimal calculateRating(){
		this.rating = new BigDecimal(this.reviews.stream().mapToDouble(review -> review.getRating()).average().getAsDouble());
		return this.rating;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

	public String getCommercialEmail() {
		return commercialEmail;
	}

	public void setCommercialEmail(String commercialEmail) {
		this.commercialEmail = commercialEmail;
	}

	public String getAdminNumber() {
		return adminNumber;
	}

	public void setAdminNumber(String adminNumber) {
		this.adminNumber = adminNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
