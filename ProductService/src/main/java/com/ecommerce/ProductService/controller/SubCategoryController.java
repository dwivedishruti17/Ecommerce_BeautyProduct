package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.entity.Subcategory;
import com.ecommerce.ProductService.service.ProductService;
import com.ecommerce.ProductService.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
@RestController
@RequestMapping("subcategories")
public class SubCategoryController {
    @Autowired
    SubCategoryService subCategoryService;
    @Autowired
    ProductService productService;

    @GetMapping("/{subcategoryId}")
    public Optional<Subcategory> getSubcategoryById(@PathVariable Long subcategoryId){
        return subCategoryService.findById(subcategoryId);
    }


}
