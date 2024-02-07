package com.cbfacademy.apiassessment.stock;

/**
 * The DuplicateStockException class is a custom RuntimeException that is thrown when there
 * is an attempt to save a duplicate stock with the same ticker in the repository.
 * It provides constructors to set a specific error message and an optional cause exception.
 *
 * @see RuntimeException
 */
public class DuplicateStockException extends RuntimeException{
    public DuplicateStockException(String message) {
        super(message);
    }
}
