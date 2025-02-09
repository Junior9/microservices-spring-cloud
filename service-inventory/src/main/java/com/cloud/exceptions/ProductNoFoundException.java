package com.cloud.exceptions;

public class ProductNoFoundException extends RuntimeException {

    public ProductNoFoundException(String message){
        super(message);
    }

}
