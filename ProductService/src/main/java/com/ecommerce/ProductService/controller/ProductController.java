package com.ecommerce.ProductService.controller;

import com.ecommerce.ProductService.DTO.FilterDto;
import com.ecommerce.ProductService.DTO.ProductDTO;
import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.security.JwtValidator;
import com.ecommerce.ProductService.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000"})
@RestController
@RequestMapping("products")
public class ProductController {
    @Autowired
 private ProductService productService;
    @Autowired
    private JwtValidator jwtValidator;
    @PostMapping("/filter")
    public List<Product> getAllProduct(@RequestBody FilterDto filterDto){

        return productService.getFilteredProducts(filterDto);

    }
    @GetMapping("{id}")
    public Optional<Product> getProductById(@PathVariable @Valid Long id){
        return productService.getProductById(id);
    }


    @PostMapping
    public ResponseEntity<?> createProduct(@Valid  @RequestBody ProductDTO productdto, @RequestHeader("Authorization") String token) {
        String role = jwtValidator.extractRole(token.substring(7));
        System.out.println("roleeeeee"+ role);
        if(!"ROLE_ADMIN".equals(role)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied! Only Admin can add products.");
        }
        Product savedProduct =  productService.createProduct(productdto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id, @RequestHeader("Authorization") String token){
        String role = jwtValidator.extractRole(token.substring(7));
        if(!"ROLE_ADMIN".equals(role)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied! Only Admin can delete products.");
        }
        productService.deleteProduct(id);
        return ResponseEntity.ok().body("product deleted successfully!!");
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @Valid @RequestBody  ProductDTO productDTO,  @RequestHeader("Authorization") String token){
        String role = jwtValidator.extractRole(token.substring(7));
        if(!"ROLE_ADMIN".equals(role)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied! Only Admin can update products.");
        }
        Product updatedProduct = productService.updateProduct(id, productDTO);
//         ResponseEntity.ok(updatedProduct);
        return ResponseEntity.ok().body(updatedProduct);

    }


@GetMapping("/filter")
public List<Product> getFilteredProducts(@RequestBody FilterDto filterDto) {
    return productService.getFilteredProducts(filterDto);
}

    @PatchMapping("{id}/quantity")
    public ResponseEntity<?> updateProductQuantity(@PathVariable Long id, @RequestBody int quantity) {
        Product updatedProduct = productService.updateProductQuantity(id, quantity);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @GetMapping("{id}/exits")
    public Boolean productexists(@PathVariable Long id){
        return productService.productexits(id);
    }


}
