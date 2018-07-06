package com.truenorth.repository;

import org.springframework.data.repository.CrudRepository;

import com.truenorth.domain.Order;

public interface DeliveryRepository extends CrudRepository<Order, Long>{

}
