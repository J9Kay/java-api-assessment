package com.cbfacademy.apiassessment.stock;

public class DuplicateStockException extends RuntimeException{
    public DuplicateStockException(String message) {
        super(message);
    }
}
