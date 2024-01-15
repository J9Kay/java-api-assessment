package com.cbfacademy.apiassessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class StockNotFoundExceptionTests {
    @Test
    @DisplayName("Check for exception on invalid stock retrieval")
    public void testExceptionOnInvalidStock() {
        StockService service = new StockService();

        assertThrows(StockNotFoundException.class, () -> {
            service.getStock("INVALID");
        }, "Should throw an exception for non-existent stock");
    }
}