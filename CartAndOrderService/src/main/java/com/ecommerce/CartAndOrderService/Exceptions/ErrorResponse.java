package com.ecommerce.CartAndOrderService.Exceptions;

import java.util.Date;

public class ErrorResponse {
    private int status;
    private String error;
    private String message;
//    private String path;
//    private Date timestamp;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
