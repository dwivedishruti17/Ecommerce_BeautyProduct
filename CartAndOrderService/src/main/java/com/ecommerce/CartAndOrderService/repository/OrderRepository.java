package com.ecommerce.CartAndOrderService.repository;

import com.ecommerce.CartAndOrderService.Entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableMongoRepositories
public interface OrderRepository extends MongoRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
