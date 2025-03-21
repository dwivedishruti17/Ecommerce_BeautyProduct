package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.entity.Category;
import com.ecommerce.ProductService.exception.CategoryNotFoundException;
import com.ecommerce.ProductService.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
@Service
public class CategoryService {
    private static final Logger logger = Logger.getLogger(CategoryService.class.getName());

    @Autowired
    private CategoryRepo categoryRepo;

    public List<Category> getAllCategory() {
        logger.info("Fetching all categories");
        List<Category> categories = categoryRepo.findAll();
        logger.info("Categories fetched successfully");
        return categories;
    }

    public Optional<Category> getCategoryByid(Long id){

        return Optional.ofNullable(categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException("No category found for categoryId:" + id)));

    }
}
