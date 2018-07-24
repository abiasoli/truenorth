package com.truenorth.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truenorth.dto.DeliveryGeoDto;
import com.truenorth.dto.DeliveryNotificationDto;
import com.truenorth.dto.OrderDto;
import com.truenorth.dto.RestaurantDto;
import com.truenorth.utils.DateUtils;

import javassist.NotFoundException;

@Service
public class DeliveryService {

	private OrderService orderService;
	private GeoService geoService;
	private OrderNotifier orderNotifier;
	private DateUtils dateUtils;
	
	@Autowired
	public DeliveryService(OrderService orderService, GeoService geoService,
			OrderNotifier orderNotifier, DateUtils dateUtils){
		this.orderService = orderService;
		this.geoService = geoService;
		this.orderNotifier = orderNotifier;
		this.dateUtils = dateUtils;
	}
	
	public void deliverOrder(OrderDto orderDto) throws NotFoundException {
		
		DeliveryGeoDto geoDto = orderService.validateOrder(orderDto);
		RestaurantDto restaurantDto = geoDto.getRestaurantDto();
		LocalDateTime eta = geoService.calculateETA(restaurantDto.getLocation(), geoDto.getDeliveryLocation(), geoDto.getMeals());
		orderService.createOrder(orderDto, eta);
		orderDto.setEta(dateUtils.formatETA(eta));
		orderNotifier.sendDeliveryNotification(new DeliveryNotificationDto(orderDto, restaurantDto.getCommercialEmail(), restaurantDto.getCommercialName()));
	}

	
}
