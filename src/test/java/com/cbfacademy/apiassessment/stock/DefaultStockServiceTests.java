package com.cbfacademy.apiassessment.stock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;

public class DefaultStockServiceTests {

    private DefaultStockService stockService;

    @BeforeEach
    void setUp() {
        stockService = new DefaultStockService();
        // Initialize with some dummy data if necessary
        stockService.saveStock(new Stock("AAPL", "Apple Inc.", "$", "Technology", 130.75, 10, 120.50));
        // Add more stocks as needed
    }

    @Test
    void testGetAllStocks() {
        Map<String, Stock> stocks = stockService.getAllStocks();
        assertFalse(stocks.isEmpty(), "Stocks should not be empty");
        assertTrue(stocks.containsKey("AAPL"), "Stocks should contain AAPL");
    }

    @Test
    void testGetStockByTicker() {
        Stock stock = stockService.getStockByTicker("AAPL");
        assertNotNull(stock, "Stock should not be null");
        assertEquals("Apple Inc.", stock.getName(), "Stock name should be Apple Inc.");

        // Test for a non-existing ticker
        assertNull(stockService.getStockByTicker("XYZ"), "Should return null for non-existing ticker");
    }

    @Test
    void testSaveStock() {
        Stock newStock = new Stock("TSLA", "Tesla Inc.", "$", "Automotive", 700.00, 5, 650.00);
        stockService.saveStock(newStock);
        assertNotNull(stockService.getStockByTicker("TSLA"), "TSLA stock should be saved and retrievable");
    }

    @Test
    void testUpdateStock() {
        Stock updatedStock = new Stock("AAPL", "Apple Inc.", "$", "Technology", 135.00, 10, 120.50);
        stockService.updateStock("AAPL", updatedStock);
        assertEquals(135.00, stockService.getStockByTicker("AAPL").getCurrentPrice(), "AAPL price should be updated");

        // Test updating non-existing stock
        assertNull(stockService.updateStock("XYZ", new Stock()), "Updating non-existing stock should return null");
    }
    @Test
    void testDeleteStock() {
        stockService.deleteStock("AAPL");
        assertNull(stockService.getStockByTicker("AAPL"), "AAPL stock should be deleted");

        // Test deleting non-existing stock
        assertDoesNotThrow(() -> stockService.deleteStock("XYZ"), "Deleting non-existing stock should not throw exception");
    }





}
