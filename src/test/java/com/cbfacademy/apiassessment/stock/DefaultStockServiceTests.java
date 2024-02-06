package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.search.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * This class contains unit tests for the DefaultStockService class,
 * ensuring that the stock management functionalities work as expected.
 * It uses Mockito to mock the StockRepository and Search dependencies,
 * allowing us to test the service layer in isolation from the actual data layer.
 */
class DefaultStockServiceTests {
    @Mock
    private StockRepository stockRepository;

    @Mock
    private Search search;

    @InjectMocks
    private DefaultStockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStocksTest() {
        when(stockRepository.retrieveAll()).thenReturn(Arrays.asList(createStockWithTicker("AAPL"), createStockWithTicker("GOOGL")));
        List<Stock> stocks = stockService.getAllStocks();
        assertNotNull(stocks);
        assertEquals(2, stocks.size());
        verify(stockRepository, times(1)).retrieveAll();
    }

    @Test
    void saveStockTest() {
        Stock newStock = createStockWithTicker("TSLA");
        when(stockRepository.save(newStock)).thenReturn(newStock);
        Stock savedStock = stockService.saveStock(newStock);
        assertEquals("TSLA", savedStock.getTicker());
        verify(stockRepository).save(newStock);
    }

    @Test
    void updateStockTest() {
        // Arrange
        Stock existingStock = createStockWithTicker("AAPL");
        Stock updatedStock = new Stock("AAPL", "Apple Inc. Updated", "$", "Technology", 150.0, 50, 95.0);
        when(stockRepository.findById("AAPL")).thenReturn(existingStock);
        when(stockRepository.update(updatedStock)).thenReturn(updatedStock);

        // Act
        Stock result = stockService.updateStock(updatedStock);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(updatedStock.getName(), result.getName(), "The names should match after update");
        assertEquals(updatedStock.getCurrentPrice(), result.getCurrentPrice(), "The prices should match after update");
        verify(stockRepository).update(updatedStock);
    }

    @Test
    void updateStockTestNotExistentStock() {
        Stock existingStock = createStockWithTicker("AAPL");
        when(stockRepository.findById("AAPL")).thenReturn(null); // Simulate stock not found
        Exception exception = assertThrows(StockNotFoundException.class, () -> {
            stockService.updateStock(existingStock);
        });
        String expectedMessage = "Stock with ticker AAPL not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
        verify(stockRepository, never()).update(any(Stock.class)); // Ensure update is never called
    }

    @Test
    void deleteStockTest() {
        doNothing().when(stockRepository).delete("AAPL");
        stockService.deleteStock("AAPL");
        verify(stockRepository).delete("AAPL");
    }

    @Test
    void searchByTickerTest() {
        Stock expectedStock = createStockWithTicker("AAPL");
        when(search.searchByTicker(anyList(), eq("AAPL"))).thenReturn(expectedStock);
        Stock foundStock = stockService.searchByTicker(Arrays.asList(createStockWithTicker("AAPL")), "AAPL");
        assertEquals("AAPL", foundStock.getTicker());
        verify(search).searchByTicker(anyList(), eq("AAPL"));
    }

    /**
     * Helper method creates a `Stock` instance with provided ticker
     * and predefined data for other fields, specific to technology sector.
     *
     * @param ticker Value of stock's ticker
     * @return New instance of `Stock` with provided ticker and predefined other fields data.
     */
    private Stock createStockWithTicker(String ticker) {
        return new Stock(ticker, "Some Company", "$", "Technology", 100.0, 50, 95.0);
    }

    @Test
    void getStockByTickerTest() {
        Stock expectedStock = createStockWithTicker("AAPL");
        when(stockRepository.findById("AAPL")).thenReturn(expectedStock);
        Stock foundStock = stockService.getStockByTicker("AAPL");
        assertNotNull(foundStock, "Found stock should not be null");
        assertEquals("AAPL", foundStock.getTicker(), "Ticker should match");
    }

    @Test
    void searchBySectorTest() {
        List<Stock> expectedStocks = Arrays.asList(createStockWithTicker("AAPL"), createStockWithTicker("GOOGL"));
        when(search.searchBySector(anyList(), eq("Technology"))).thenReturn(expectedStocks);
        List<Stock> foundStocks = stockService.searchBySector("Technology");
        assertNotNull(foundStocks);
        assertEquals(2, foundStocks.size());
        verify(search).searchBySector(anyList(), eq("Technology"));
    }

    @Test
    void sortByAttributeTest() {
        List<Stock> expectedStocks = Arrays.asList(createStockWithTicker("AAPL"), createStockWithTicker("GOOGL"));
        when(stockRepository.sortByAttribute("name")).thenReturn(expectedStocks);
        List<Stock> sortedStocks = stockService.sortByAttribute("name");
        assertNotNull(sortedStocks);
        assertEquals(2, sortedStocks.size());
        verify(stockRepository).sortByAttribute("name");
    }

    @Test
    void getStockByTickerNotFoundTest() {
        when(stockRepository.findById("AAPL")).thenReturn(null);
        Stock foundStock = stockService.getStockByTicker("AAPL");
        assertNull(foundStock, "Should return null for non-existing ticker");
    }
}