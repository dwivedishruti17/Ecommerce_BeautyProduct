package com.ecommerce.ProductService.repository;

import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListResourceBundle;
import java.util.Optional;

@Repository
public interface SubCategoryRepo extends JpaRepository<Subcategory, Long> {
    List<Subcategory> findByCategoryId(Long categoryId);

    @Query("SELECT s from Subcategory s WHERE LOWER(s.name)=LOWER(:name) ")
    Optional<Subcategory> findByName(String name);
//    @Query("SELECT p FROM Product p JOIN p.subcategory s JOIN s.category c WHERE s.id=?2 AND c.id=?1 ")
    @Query("SELECT p FROM Product p WHERE p.subcategory.id = :subcategoryId AND p.subcategory.category.id = :categoryId")
    List<Product> findByCategoryIdAndIdABC(@Param("categoryId") Long categoryId,@Param("subcategoryId") Long id);




}
