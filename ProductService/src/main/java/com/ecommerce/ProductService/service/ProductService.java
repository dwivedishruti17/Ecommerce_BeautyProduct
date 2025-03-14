package com.ecommerce.ProductService.service;
import com.ecommerce.ProductService.DTO.FilterDto;
import com.ecommerce.ProductService.DTO.ProductDTO;
import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.entity.Subcategory;
import com.ecommerce.ProductService.exception.ProductNotFoundException;
import com.ecommerce.ProductService.exception.SubcategoryNotFoundException;
import com.ecommerce.ProductService.repository.CategoryRepo;
import com.ecommerce.ProductService.repository.ProductRepo;
import com.ecommerce.ProductService.repository.SubCategoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private SubCategoryRepo subCategoryRepo;
    @Autowired
    private CategoryRepo categoryRepo;

//    private static final Map<String, Integer> SUBCATEGORY_MAP = Map.ofEntries(
//            Map.entry("Tints", 1),
//            Map.entry("Blush", 2),
//            Map.entry("Nail Paint", 3),
//            Map.entry("Lipstick", 4),
//            Map.entry("Sunscreen", 5),
//            Map.entry("Body Lotion", 6),
//            Map.entry("Face Serum", 7),
//            Map.entry("Shampoo", 8),
//            Map.entry("Conditioner", 9),
//            Map.entry("Hair Oil", 10),
//            Map.entry("Perfume", 11),
//            Map.entry("Deodorant", 12),
//            Map.entry("Body Wash", 13),
//            Map.entry("Soap", 14)
//    );

    public List<Product> getAllProd() {
        List<Product> products = productRepo.findAll();
        products.forEach(product -> System.out.println("ye mere products hain......."+product));
        return products;
    }

    public Optional<Product> getProductById(Long id){
//         Optional<Product> product = productRepo.findById(id);
        return Optional.ofNullable(productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found for ID: " + id)));

    }

    @Transactional
    public Product createProduct(ProductDTO productDTO){
//        Integer subcategoryId = SUBCATEGORY_MAP.get(productDTO.getSubcategoryName());
        Subcategory subcategory = subCategoryRepo.findByName(productDTO.getSubcategoryName())
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));


//        Category category = categoryRepo.findById(subcategory.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setSubcategoryId(subcategory.getId());
        product.setSubcategory(subcategory);
        product.setImageUrl(productDTO.getImageUrl());
        product.setQuantity(productDTO.getQuantity());
        return productRepo.save(product);
    }

    public ResponseEntity<String> deleteProduct(Long id){
        Boolean exists = productRepo.existsById(id);
        System.out.println("existssss : "+ exists);
        Product product = productRepo.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found"));
        System.out.println("produdttt : "+ product);

        productRepo.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully");
    }


    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {

        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if(productDTO.getName()!=null) {
            existingProduct.setName(productDTO.getName());
        }
        if(productDTO.getDescription()!=null) {
            existingProduct.setDescription(productDTO.getDescription());
        }
        if(productDTO.getPrice()!=null) {
            existingProduct.setPrice(productDTO.getPrice());
        }
        if(productDTO.getImageUrl()!=null){
            existingProduct.setImageUrl(productDTO.getImageUrl());
        }
        if(productDTO.getQuantity()!=null){
            existingProduct.setQuantity(productDTO.getQuantity());
        }
        if(productDTO.getSubcategoryName()!=null){
            Subcategory subcategory = subCategoryRepo.findByName(productDTO.getSubcategoryName())
                    .orElseThrow(() -> new SubcategoryNotFoundException("Subcategory not found"));


            existingProduct.setSubcategoryId(subcategory.getId());
            existingProduct.setSubcategory(subcategory);
        }

        return productRepo.save(existingProduct);
    }

    public List<Product> getFilteredProducts(FilterDto filterDto){
        Double minPrice = filterDto.getMinPrice()!=null? filterDto.getMinPrice() : 0.0;
        Double maxPrice = filterDto.getMaxPrice()!=null? filterDto.getMaxPrice() : Double.MAX_VALUE;
        String name = filterDto.getName() != null ? filterDto.getName() : "";
        String order = filterDto.getOrder() != null ? filterDto.getOrder() : "asc";
        String sortBy = filterDto.getSortBy() != null ? filterDto.getSortBy() : "price";

        Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        return productRepo.findFilteredProducts(minPrice, maxPrice, name, sort);


    }

    public Product updateProductQuantity(Long id, int quantity) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(quantity);
            return productRepo.save(product);
        } else {
            throw new ProductNotFoundException("Product not found with id " + id);
        }
    }

    public boolean productexits(Long productId){
      return  productRepo.existsById(productId);
    }



}

