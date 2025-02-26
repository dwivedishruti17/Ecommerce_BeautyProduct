package com.ecommerce.ProductService.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message, Throwable cause){
        super(message);
    }
    public ProductNotFoundException(String message) {
        super(message);
    }
}
