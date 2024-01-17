package com.cbfacademy.apiassessment.core;

import java.io.IOException;

public class PersistenceException extends RuntimeException{
    public PersistenceException(String message, IOException e) {
        super(message);
    }

}
