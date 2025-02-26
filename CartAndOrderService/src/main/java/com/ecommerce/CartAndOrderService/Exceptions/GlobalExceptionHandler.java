package com.ecommerce.CartAndOrderService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", new Date());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", "Product Not Found");
        body.put("message", ex.getMessage());
//        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<Object> handleProductOutOfStockException(ProductOutOfStockException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
//        body.put("error", "Product Out of Stock");
        body.put("message", ex.getMessage());
//        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
