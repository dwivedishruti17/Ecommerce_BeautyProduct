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

//@GetMapping("/{categoryId}/{subcategoryId}")
//public ResponseEntity<Subcategory> getSubcategoryByCategoryIdAndSubcategoryId(@PathVariable Long categoryId, @PathVariable Long subcategoryId) {
////    logger.info("Received request for categoryId: {} and subcategoryId: {}");
//    return new ResponseEntity<>(subCategoryService.findSubcategoryBycategoryIdAndSubcategoryId(categoryId, subcategoryId), HttpStatus.ACCEPTED);
//
//}
    @GetMapping("/{subcategoryId}")
    public Optional<Subcategory> getSubcategoryById(@PathVariable Long subcategoryId){
        return subCategoryService.findById(subcategoryId);
    }
//@GetMapping("/{subcategoryId}/{productId}")
//public ResponseEntity<Product> getProductBySubcategoryIdAndProductId(
//        @PathVariable Long subcategoryId,
//        @PathVariable Long productId) {
//    Optional<Product> product = productService.getProductBySubcategoryIdAndProductId(subcategoryId, productId);
//    return product.map(ResponseEntity::ok)
//            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//}


}
