package com.cbfacademy.apiassessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StockServiceTests {
    @Test
    @DisplayName("Check retrieving a stock by ticker")
    public void testGetStock() {
        StockService service = new StockService();
        service.addStock(new Stock("AAPL", "Apple Inc.", "$", 150.50, 10, 145.00));

        Stock stock = service.getStock("AAPL");
        assertNotNull(stock, "Stock AAPL should be retrievable");
        assertEquals("Apple Inc.", stock.getName(), "Name should match");
    }



}