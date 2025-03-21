package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.entity.Category;
import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.entity.Subcategory;
import com.ecommerce.ProductService.repository.SubCategoryRepo;
import com.ecommerce.ProductService.service.CategoryService;
import com.ecommerce.ProductService.service.ProductService;
import com.ecommerce.ProductService.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
@RestController
@RequestMapping("categories")
public class CategoryController {
@Autowired
    CategoryService categoryService;
@Autowired
ProductService productService;
@Autowired
    SubCategoryService subCategoryService;
@Autowired
    SubCategoryRepo subCategoryRepo;

@GetMapping()
    public List<Category> getAllCategory(){
    return categoryService.getAllCategory();
}

@GetMapping("{id}")
    public Optional<Category> getCategoryById(@PathVariable Long id){

    return categoryService.getCategoryByid(id);
}

@GetMapping("{categoryId}/subcategories")
    public List<Subcategory> getSubcategoriesBycategoryid(@PathVariable Long categoryId){
    return subCategoryRepo.findByCategoryId(categoryId);
}


}
