package com.cbfacademy.apiassessment.stock;
/**
 * StockNotFoundException is an exception class that is thrown when a stock is not found.
 * This exception extends the RuntimeException class.
 */
public class StockNotFoundException extends RuntimeException{
        public StockNotFoundException(String message) {
            super(message);
        }

}
