package com.cbfacademy.apiassessment.core;

/**
 * The PersistenceException class is a custom RuntimeException that is thrown when there is an issue with the persistence layer.
 * It provides constructors to set a specific error message and an optional cause exception.
 */
public class PersistenceException extends RuntimeException {
    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
