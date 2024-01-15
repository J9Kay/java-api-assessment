package com.cbfacademy.apiassessment;
public class StockNotFoundException extends RuntimeException{
        public StockNotFoundException(String message) {
            super(message);
        }

}
