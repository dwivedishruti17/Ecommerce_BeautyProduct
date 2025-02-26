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
    List<Product> findBySubcategoryId(Long subcategoryId);
//    @Query("SELECT p FROM Product p WHERE p.id = :productId AND p.subcategory.id = :subcategoryId AND p.subcategory.category.id = :categoryId")
//    Optional<Product> findByCategoryIdAndSubcategoryIdAndProductId(@Param("categoryId") Long categoryId, @Param("subcategoryId") Long subcategoryId,@Param("productId") Long productId);

//    @Query("SELECT p FROM Product p WHERE p.subcategory.id = :subcategoryId AND p.id = :productId")
//    Optional<Product> findBySubcategoryIdAndProductId(@Param("subcategoryId") Long subcategoryId, @Param("productId") Long productId);

//    @Query("SELECT p FROM Product p WHERE " +
//            "(:subcategory IS NULL OR p.subcategory = :subcategory) AND " +
//            "(p.price BETWEEN :minPrice AND :maxPrice)")
//
//    List<Product> findFilteredProducts(@Param("subcategory") String subcategory,
//                                       @Param("minPrice") Double minPrice,
//                                       @Param("maxPrice") Double maxPrice,
//                                       Sort sort);
//@Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
@Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.name LIKE %:name%")
List<Product> findFilteredProducts(@Param("minPrice") Double minPrice,
                                   @Param("maxPrice") Double maxPrice,
                                   @Param("name")String name,
                                   Sort sort);

}
