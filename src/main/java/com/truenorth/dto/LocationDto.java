package com.truenorth.dto;

import javax.validation.constraints.NotBlank;

public class LocationDto {

	@NotBlank
	private double latitude;
	@NotBlank
	private double longitude;

	public LocationDto(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public LocationDto(){};

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "LocationDto [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
}
