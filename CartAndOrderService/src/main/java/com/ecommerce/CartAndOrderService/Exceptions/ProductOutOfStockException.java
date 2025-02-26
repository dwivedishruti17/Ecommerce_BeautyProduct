package com.ecommerce.CartAndOrderService.Exceptions;

public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException(String message){
        super(message);
    }
}
