package com.ecommerce.ProductService.service;

import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.entity.Subcategory;
import com.ecommerce.ProductService.exception.CategoryNotFoundException;
import com.ecommerce.ProductService.exception.SubcategoryNotFoundException;
import com.ecommerce.ProductService.repository.CategoryRepo;
import com.ecommerce.ProductService.repository.SubCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepo subCategoryRepo;
    @Autowired
    private CategoryRepo categoryRepo;


    public Optional<Subcategory> findById(Long subcategoryId){
        return Optional.ofNullable(subCategoryRepo.findById(subcategoryId).orElseThrow(() -> new SubcategoryNotFoundException("sucategory not found with id" + subcategoryId)));
    }

}
