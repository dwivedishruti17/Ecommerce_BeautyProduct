package com.ecommerce.CartAndOrderService.Exceptions;

public class ItemAlreadyExists extends RuntimeException{
    public ItemAlreadyExists(String message){
        super(message);
    }
}
