package com.truenorth.repository;

import org.springframework.data.repository.CrudRepository;

import com.truenorth.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}
