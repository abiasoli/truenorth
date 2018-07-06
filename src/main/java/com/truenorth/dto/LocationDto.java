package com.truenorth.dto;

import javax.validation.constraints.NotBlank;

public class LocationDto {

	@NotBlank
	private String latitude;
	@NotBlank
	private String longitude;

	public LocationDto(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public LocationDto(){};

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "LocationDto [latitude=" + latitude + ", longitude=" + longitude + "]";
	}
	
}
