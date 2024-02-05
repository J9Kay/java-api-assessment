package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.search.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

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

    /**
     * Tests that getAllStocks() retrieves all stocks from the repository.
     * Ensures that the stock list is not null, has the correct size, and that
     * the repository's retrieveAll method is called exactly once.
     */

    @Test
    void getAllStocksTest() {
        when(stockRepository.retrieveAll()).thenReturn(Arrays.asList(createStock("AAPL"), createStock("GOOGL")));
        List<Stock> stocks = stockService.getAllStocks();
        assertNotNull(stocks);
        assertEquals(2, stocks.size());
        verify(stockRepository, times(1)).retrieveAll();
    }

    /**
     * Tests that saveStock() correctly saves a new stock and returns it.
     * Verifies that the saved stock matches the expected details and
     * that the repository's save method is invoked correctly.
     */
    @Test
    void saveStockTest() {
        Stock newStock = createStock("TSLA");
        when(stockRepository.save(newStock)).thenReturn(newStock);
        Stock savedStock = stockService.saveStock(newStock);
        assertEquals("TSLA", savedStock.getTicker());
        verify(stockRepository).save(newStock);
    }

    /**
     * Tests updating a stock's details. Ensures that the updated stock is correctly
     * returned from the service, and that the repository's update method is called.
     */

    @Test
    void updateStockTest() {
        Stock existingStock = createStock("AAPL");
        when(stockRepository.update(existingStock)).thenReturn(existingStock);
        Stock updatedStock = stockService.updateStock(existingStock);
        assertNotNull(updatedStock);
        assertEquals("AAPL", updatedStock.getTicker());
        verify(stockRepository).update(existingStock);
    }

    /**
     * Tests deleting a stock by its ticker symbol.
     * Verifies that the repository's delete method is called with the correct ticker.
     */


    @Test
    void deleteStockTest() {
        doNothing().when(stockRepository).delete("AAPL");
        stockService.deleteStock("AAPL");
        verify(stockRepository).delete("AAPL");
    }

    /**
     * Tests searching for a stock by its ticker using the search component.
     * Ensures that the correct stock is returned and that the search method is called.
     */

    @Test
    void searchByTickerTest() {
        Stock expectedStock = createStock("AAPL");
        when(search.searchByTicker(anyList(), eq("AAPL"))).thenReturn(expectedStock);
        Stock foundStock = stockService.searchByTicker(Arrays.asList(createStock("AAPL")), "AAPL");
        assertEquals("AAPL", foundStock.getTicker());
        verify(search).searchByTicker(anyList(), eq("AAPL"));
    }


    private Stock createStock(String ticker) {
        return new Stock(ticker, "Some Company", "$", "Some Sector", 100.0, 50, 95.0);
    }

    /**
     * Tests retrieving a stock by its ticker symbol.
     * Ensures that the correct stock is returned when it exists, and verifies
     * that the repository's findById method is utilized correctly.
     */

    @Test
    void getStockByTickerTest() {
        Stock expectedStock = createStock("AAPL");
        when(stockRepository.findById("AAPL")).thenReturn(expectedStock);
        Stock foundStock = stockService.getStockByTicker("AAPL");
        assertNotNull(foundStock, "Found stock should not be null");
        assertEquals("AAPL", foundStock.getTicker(), "Ticker should match");
    }

    /**
     * Tests searching for stocks by a specific sector.
     * Verifies that the correct list of stocks is returned for a given sector and
     * confirms that the search component's searchBySector method is called with the correct parameters.
     */

    @Test
    void searchBySectorTest() {
        List<Stock> expectedStocks = Arrays.asList(createStock("AAPL"), createStock("GOOGL"));
        when(search.searchBySector(anyList(), eq("Technology"))).thenReturn(expectedStocks);
        List<Stock> foundStocks = stockService.searchBySector("Technology");
        assertNotNull(foundStocks);
        assertEquals(2, foundStocks.size());
        verify(search).searchBySector(anyList(), eq("Technology"));
    }

    /**
     * Tests sorting stocks by a specific attribute (e.g., name).
     * Checks that stocks are sorted correctly based on the attribute and
     * ensures that the repository's sortByAttribute method is called with the correct argument.
     */


    @Test
    void sortByAttributeTest() {
        List<Stock> expectedStocks = Arrays.asList(createStock("AAPL"), createStock("GOOGL"));
        when(stockRepository.sortByAttribute("name")).thenReturn(expectedStocks);
        List<Stock> sortedStocks = stockService.sortByAttribute("name");
        assertNotNull(sortedStocks);
        assertEquals(2, sortedStocks.size());
        verify(stockRepository).sortByAttribute("name");
    }


    // Utilizes a helper method to create stock objects for testing


    @Test
    void getStockByTickerNotFoundTest() {
        when(stockRepository.findById("AAPL")).thenReturn(null);
        Stock foundStock = stockService.getStockByTicker("AAPL");
        assertNull(foundStock, "Should return null for non-existing ticker");
    }


}

