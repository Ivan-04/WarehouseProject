package com.example.warehouseproject.exceptions;

public class InsufficientPartException extends RuntimeException {

    public InsufficientPartException(String message) {
        super(message);
    }
}
