package com.cloud.exceptions;

public class OrderInvalidatedException extends  RuntimeException {
    public OrderInvalidatedException (String message) {
        super(message);
    }
}
