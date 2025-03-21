package com.ecommerce.ProductService.service;
import com.ecommerce.ProductService.DTO.FilterDto;
import com.ecommerce.ProductService.DTO.ProductDTO;
import com.ecommerce.ProductService.entity.Product;
import com.ecommerce.ProductService.entity.Subcategory;
import com.ecommerce.ProductService.exception.ProductNotFoundException;
import com.ecommerce.ProductService.exception.SubcategoryNotFoundException;
import com.ecommerce.ProductService.repository.ProductRepo;
import com.ecommerce.ProductService.repository.SubCategoryRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private SubCategoryRepo subCategoryRepo;

    public List<Product> getAllProd() {
        logger.info("Fetching all products");
        List<Product> products = productRepo.findAll();
        products.forEach(product -> logger.fine("Product: " + product));
        return products;
    }

    public Optional<Product> getProductById(Long id) {
        logger.info("Fetching product by ID: " + id);
        return Optional.ofNullable(productRepo.findById(id)
                .orElseThrow(() -> {
                    logger.severe("Product not found for ID: " + id);
                    return new ProductNotFoundException("Product not found for ID: " + id);
                }));
    }

    @Transactional
    public Product createProduct(ProductDTO productDTO) {
        logger.info("Creating product with details: " + productDTO);
        Subcategory subcategory = subCategoryRepo.findByName(productDTO.getSubcategoryName())
                .orElseThrow(() -> {
                    logger.severe("Subcategory not found: " + productDTO.getSubcategoryName());
                    return new RuntimeException("Subcategory not found");
                });

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setSubcategoryId(subcategory.getId());
        product.setSubcategory(subcategory);
        product.setImageUrl(productDTO.getImageUrl());
        product.setQuantity(productDTO.getQuantity());
        Product savedProduct = productRepo.save(product);
        logger.info("Product created successfully: " + savedProduct);
        return savedProduct;
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        logger.info("Deleting product with ID: " + id);
        Boolean exists = productRepo.existsById(id);
        logger.fine("Product exists: " + exists);
        Product product = productRepo.findById(id).orElseThrow(() -> {
            logger.severe("Product not found for ID: " + id);
            return new ProductNotFoundException("Product not found");
        });
        logger.fine("Product to be deleted: " + product);

        productRepo.deleteById(id);
        logger.info("Product deleted successfully");
        return ResponseEntity.ok("Product deleted successfully");
    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO productDTO) {
        logger.info("Updating product with ID: " + id);
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> {
                    logger.severe("Product not found for ID: " + id);
                    return new ProductNotFoundException("Product not found");
                });

        if (productDTO.getName() != null) {
            existingProduct.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            existingProduct.setDescription(productDTO.getDescription());
        }
        if (productDTO.getPrice() != null) {
            existingProduct.setPrice(productDTO.getPrice());
        }
        if (productDTO.getImageUrl() != null) {
            existingProduct.setImageUrl(productDTO.getImageUrl());
        }
        if (productDTO.getQuantity() != null) {
            existingProduct.setQuantity(productDTO.getQuantity());
        }
        if (productDTO.getSubcategoryName() != null) {
            Subcategory subcategory = subCategoryRepo.findByName(productDTO.getSubcategoryName())
                    .orElseThrow(() -> {
                        logger.severe("Subcategory not found: " + productDTO.getSubcategoryName());
                        return new SubcategoryNotFoundException("Subcategory not found");
                    });

            existingProduct.setSubcategoryId(subcategory.getId());
            existingProduct.setSubcategory(subcategory);
        }

        Product updatedProduct = productRepo.save(existingProduct);
        logger.info("Product updated successfully: " + updatedProduct);
        return updatedProduct;
    }

    public List<Product> getFilteredProducts(FilterDto filterDto) {
        logger.info("Fetching filtered products with filter: " + filterDto);
        Double minPrice = filterDto.getMinPrice() != null ? filterDto.getMinPrice() : 0.0;
        Double maxPrice = filterDto.getMaxPrice() != null ? filterDto.getMaxPrice() : Double.MAX_VALUE;
        String name = filterDto.getName() != null ? filterDto.getName() : "";
        String order = filterDto.getOrder() != null ? filterDto.getOrder() : "asc";
        String sortBy = filterDto.getSortBy() != null ? filterDto.getSortBy() : "price";

        Sort sort = Sort.by(order.equalsIgnoreCase("desc") ? Sort.Order.desc(sortBy) : Sort.Order.asc(sortBy));
        List<Product> filteredProducts = productRepo.findFilteredProducts(minPrice, maxPrice, name, sort);
        logger.info("Filtered products fetched successfully");
        return filteredProducts;
    }

    public Product updateProductQuantity(Long id, int quantity) {
        logger.info("Updating product quantity for ID: " + id);
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setQuantity(quantity);
            Product updatedProduct = productRepo.save(product);
            logger.info("Product quantity updated successfully: " + updatedProduct);
            return updatedProduct;
        } else {
            logger.severe("Product not found with ID: " + id);
            throw new ProductNotFoundException("Product not found with id " + id);
        }
    }
    public boolean productexits(Long productId){
        return  productRepo.existsById(productId);
    }


}