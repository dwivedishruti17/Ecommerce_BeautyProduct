package com.ecommerce.CartAndOrderService.repository;

import com.ecommerce.CartAndOrderService.Entity.Cart;
import com.ecommerce.CartAndOrderService.Entity.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@EnableMongoRepositories
public interface WishlistRepository extends MongoRepository<Wishlist, Long> {
    Optional<Wishlist> findByUserId(Long id);
    Optional<Wishlist> findWishlistByUserId(Long id);
//    Optional<Wishlist>
}
