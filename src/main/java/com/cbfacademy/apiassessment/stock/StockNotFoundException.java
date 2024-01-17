package com.cbfacademy.apiassessment.stock;
public class StockNotFoundException extends RuntimeException{
        public StockNotFoundException(String message) {
            super(message);
        }

}
