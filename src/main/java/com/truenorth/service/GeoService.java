package com.truenorth.service;

import java.time.LocalDateTime;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.maps.model.LatLng;
import com.truenorth.connector.GeoConnector;
import com.truenorth.dto.LocationDto;
import com.truenorth.dto.MealDto;
import com.truenorth.utils.DateUtils;

@Service
public class GeoService {

	private GeoConnector connector;
	private DateUtils dateUtils;
	
	@Autowired
	public GeoService(GeoConnector connector, DateUtils dateUtils){
		this.connector = connector;
		this.dateUtils = dateUtils;
	}
	
	@Transactional(propagation=Propagation.NEVER)
	public LocalDateTime calculateETA(LocationDto from, LocationDto to, List<MealDto> meals) {
		
		DateTime now = dateUtils.now();
		DateTime cookTime = now;
		for(MealDto meal : meals){
			cookTime = cookTime.plusMinutes(meal.getCookTime());
		}
		ReadableInstant departureTime = cookTime;
		
		LatLng origin = new LatLng(from.getLatitude(), from.getLongitude());
	    LatLng destination = new LatLng(to.getLatitude(), to.getLongitude());
		
	    int time = connector.getTransitTime(origin, destination, departureTime);
	    DateTime eta = cookTime.plusSeconds(time);
	    return dateUtils.toLocalDateTime(eta);
	}
}
