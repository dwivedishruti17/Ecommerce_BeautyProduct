package com.ecommerce.CartAndOrderService.repository;

import com.ecommerce.CartAndOrderService.Dto.OrderDto;
import com.ecommerce.CartAndOrderService.Entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableMongoRepositories
public interface OrderRepository extends MongoRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
@Query("{ '_id': ?0 }")
Optional<Order> findById(String id);
}
