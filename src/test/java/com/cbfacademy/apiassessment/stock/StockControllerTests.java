package com.cbfacademy.apiassessment.stock;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockController.class)
public class StockControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private Stock mockStock;

    @BeforeEach
    void setUp() {
        mockStock = new Stock("AAPL", "Apple Inc.", "$", "Technology", 130.75, 10, 120.50);
    }

    @Test
    void testGetAllStocks() throws Exception {
        Map<String, Stock> stocks = new HashMap<>();
        stocks.put("AAPL", mockStock);

        given(stockService.getAllStocks()).willReturn(stocks);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$.AAPL.name").value("Apple Inc."));
    }

    @Test
    void testGetStockByTicker() throws Exception {
        given(stockService.getStockByTicker("AAPL")).willReturn(mockStock);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stocks/AAPL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Apple Inc."));
    }

    @Test
    void testGetStockByTickerNotFound() throws Exception {
        given(stockService.getStockByTicker("UNKNOWN")).willReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stocks/UNKNOWN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveStock() throws Exception {
        given(stockService.saveStock(any(Stock.class))).willReturn(mockStock);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/stocks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ticker\": \"AAPL\", \"name\": \"Apple Inc.\", \"currencySymbol\": \"$\", \"sector\": \"Technology\", \"currentPrice\": 130.75, \"quantity\": 10, \"purchasePrice\": 120.50 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Apple Inc."));
    }

    @Test
    void testUpdateStock() throws Exception {
        given(stockService.updateStock(eq("AAPL"), any(Stock.class))).willReturn(mockStock);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/stocks/AAPL")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"ticker\": \"AAPL\", \"name\": \"Apple Inc.\", \"currencySymbol\": \"$\", \"sector\": \"Technology\", \"currentPrice\": 140.00, \"quantity\": 10, \"purchasePrice\": 120.50 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPrice").value(130.75));
    }

    @Test
    void testDeleteStock() throws Exception {
        doNothing().when(stockService).deleteStock("AAPL");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stocks/AAPL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
