package com.ecommerce.CartAndOrderService.repository;

import com.ecommerce.CartAndOrderService.Entity.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableMongoRepositories
public interface CartRepository extends MongoRepository<Cart, Long> {
    Optional<Cart> findCartByUserId(Long userId);
    Optional<Cart> deleteCartByUserId(Long userId);
}
