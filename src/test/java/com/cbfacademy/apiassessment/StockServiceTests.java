package com.cbfacademy.apiassessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class StockServiceTests {
    @Test
    @DisplayName("Check stock is correctly deleted")
    public void testDeleteStock() {
        StockService service = new StockService();
        service.addStock(new Stock("AAPL", "Apple Inc.", "$", 150.50, 10, 145.00));
        service.deleteStock("AAPL");

        assertNull(service.getStock("AAPL"), "Stock AAPL should be deleted");
    }

    @Test
    @DisplayName("Check retrieving a stock by ticker")
    public void testGetStock() {
        StockService service = new StockService();
        service.addStock(new Stock("AAPL", "Apple Inc.", "$", 150.50, 10, 145.00));

        Stock stock = service.getStock("AAPL");
        assertNotNull(stock, "Stock AAPL should be retrievable");
        assertEquals("Apple Inc.", stock.getName(), "Name should match");
    }

    @Test
    @DisplayName("Check total portfolio value calculation")
    public void testTotalPortfolioValue() {
        StockService service = new StockService();
        service.addStock(new Stock("AAPL", "Apple Inc.", "$", 150.50, 10, 145.00));
        service.addStock(new Stock("MSFT", "Microsoft Corporation", "$", 210.40, 5, 200.00));

        double totalValue = service.calculateTotalPortfolioValue();
        assertEquals(3755.00, totalValue, 0.01, "Total portfolio value should be correctly calculated");
    }

    @Test
    @DisplayName("Check ROI calculation")
    public void testROICalculation() {
        StockService service = new StockService();
        service.addStock(new Stock("AAPL", "Apple Inc.", "$", 160.00, 10, 145.00));

        double roi = service.calculateROI("AAPL");
        assertEquals(10.34, roi, 0.01, "ROI should be correctly calculated");
    }


}
