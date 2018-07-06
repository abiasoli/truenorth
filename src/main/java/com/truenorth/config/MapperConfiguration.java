package com.truenorth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.truenorth.domain.Order;
import com.truenorth.domain.Restaurant;
import com.truenorth.dto.OrderDto;
import com.truenorth.dto.RestaurantDto;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Configuration
public class MapperConfiguration {

    @Bean
    public MapperFacade mapper() {

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(RestaurantDto.class, Restaurant.class).byDefault().register();
        mapperFactory.classMap(Restaurant.class, RestaurantDto.class).byDefault().register();
        mapperFactory.classMap(OrderDto.class, Order.class).byDefault().register();
        mapperFactory.classMap(Order.class, OrderDto.class).byDefault().register();
        return mapperFactory.getMapperFacade();
    }
}
