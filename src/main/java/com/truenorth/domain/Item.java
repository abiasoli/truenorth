package com.truenorth.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Item {

	@Column(nullable=false)
	private String name;
	
	public Item(String name) {
		this.name = name;
	}

	public Item(){}
	
	public abstract Long getId();

	public abstract void setId(Long id);
	
	public abstract void setRestaurant(Restaurant restaurant);

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	

}
