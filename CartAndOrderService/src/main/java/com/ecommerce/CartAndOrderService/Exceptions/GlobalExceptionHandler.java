package com.ecommerce.CartAndOrderService.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)

      protected ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ItemAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleItemAlreadyExistsException(ItemAlreadyExists ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.CONFLICT.value());
        errorResponse.setError("Conflict");
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
//        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", new Date());
        errorResponse.setStatus( HttpStatus.NOT_FOUND.value());
        errorResponse.setError("Product Not Found");
        errorResponse.setMessage( ex.getMessage());
//        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<ErrorResponse> handleProductOutOfStockException(ProductOutOfStockException ex, WebRequest request) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", new Date());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
//        body.put("error", "Product Out of Stock");
        errorResponse.setMessage(ex.getMessage());
//        body.put("path", request.getDescription(false).substring(4));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError("Not found");
        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setPath(request.getDescription(false).substring(4));
//        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError("Bad Request");
        errorResponse.setMessage(ex.getMessage());
//        errorResponse.setPath(request.getDescription(false).substring(4));
//        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }





}
