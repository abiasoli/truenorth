package com.truenorth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.truenorth.dto.OrderDto;
import com.truenorth.service.DeliveryService;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/truenorth/delivery")
public class DeliveryController {
	
	private DeliveryService deliveryService;
	
	@Autowired
	public DeliveryController(DeliveryService deliveryService){
		this.deliveryService = deliveryService;
	}
	
	@RequestMapping(value = "/order", method = RequestMethod.POST)
    public void addOrder(@RequestBody OrderDto orderDto) throws NotFoundException { 
		deliveryService.deliverOrder(orderDto);
	}
}
