package com.example.warehouseproject.exceptions;

public class UnauthorizedOperationException extends RuntimeException {
    public UnauthorizedOperationException(String message){
        super(message);
    }
}