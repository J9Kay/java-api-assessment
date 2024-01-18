package com.cbfacademy.apiassessment.stock;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTests {
    @Test
    @DisplayName("Check stock is created with all fields")
    public void testStockCreation() {
        // Assuming the constructor only sets the final fields
        Stock stock = new Stock("AAPL", "Apple Inc.", "$", "tech");

        // Set mutable fields
        stock.setCurrentPrice(150.50);
        stock.setQuantity(10);
        stock.setPurchasePrice(145.00);

        // Assertions for all fields
        assertEquals("AAPL", stock.getTicker(), "Ticker should be AAPL");
        assertEquals("Apple Inc.", stock.getName(), "Name should be Apple Inc.");
        assertEquals("$", stock.getCurrencySymbol(), "Currency symbol should be $");
        assertEquals("tech", stock.getSector(), "Sector should be tech");
        assertEquals(150.50, stock.getCurrentPrice(), 0.01, "Price should be 150.50");
        assertEquals(10, stock.getQuantity(), "Quantity should be 10");
        assertEquals(145.00, stock.getPurchasePrice(), 0.01, "Purchase price should be 145.00");
    }
}
