package com.cbfacademy.apiassessment.core;

/**
 * The PersistenceException class is a custom RuntimeException that is thrown when there is an issue with
 * the persistence layer.
 * It provides constructors to set a specific error message and an optional cause exception.
 */
public class PersistenceException extends RuntimeException {
    /**
     * Constructs a new PersistenceException with the specified detail message.This constructor is useful
     * when you want to throw a PersistenceException with a custom error message but without any underlying cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public PersistenceException(String message) {
        super(message);
    }

    /**
     * Constructs a new PersistenceException with the specified detail message and cause.This constructor
     * is useful when you want to provide more information about the cause of the persistence issue
     * that led to the exception.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause the cause (which is saved for later retrieval by the getCause() method)
     */

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
