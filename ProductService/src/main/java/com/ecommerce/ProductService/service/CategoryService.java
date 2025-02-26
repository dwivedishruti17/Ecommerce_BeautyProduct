package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.entity.Category;
import com.ecommerce.ProductService.exception.CategoryNotFoundException;
import com.ecommerce.ProductService.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    public List<Category> getAllCategory(){
       return categoryRepo.findAll();
    }

    public Optional<Category> getCategoryByid(Long id){


        return Optional.ofNullable(categoryRepo.findById(id).orElseThrow(() -> new CategoryNotFoundException("No category found for categoryId:" + id)));

    }


}
