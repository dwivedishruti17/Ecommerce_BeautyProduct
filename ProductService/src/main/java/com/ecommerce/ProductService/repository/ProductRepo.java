package com.ecommerce.ProductService.repository;

import com.ecommerce.ProductService.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

@Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.name LIKE %:name%")
List<Product> findFilteredProducts(@Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice,
                                   @Param("name")String name,
                                   Sort sort);

}
