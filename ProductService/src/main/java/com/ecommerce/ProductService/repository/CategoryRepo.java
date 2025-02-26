package com.ecommerce.ProductService.repository;

import com.ecommerce.ProductService.entity.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

}
