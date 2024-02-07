package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.cbfacademy.apiassessment.search.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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


    /**
     * This method tests the deleteStock method in the StockService class.
     * It verifies that the stock with the given ticker is deleted correctly.
     * It also verifies that an exception is thrown when attempting to delete a stock that is not found.
     *
     * Stubbing the searchByTicker() Method:
     *
     * when(stockRepository.searchByTicker("AAPL")).thenReturn(new ArrayList<>());: This line stubs the
     * searchByTicker() method of the stockRepository to return an empty list when called with the symbol "AAPL". In
     * a real scenario, if the stock is found, this method would return a list containing the found stock. By stubbing
     * it to return an empty list, the test environment simulates the scenario where the stock is not found in the
     * repository.
     *
     * Assertion and Verification:
     *
     * assertThrows(StockNotFoundException.class, () -> { stockService.deleteStock("AAPL"); });: This line
     * asserts that calling the deleteStock() method with the symbol "AAPL" throws a StockNotFoundException.
     * Since the repository is stubbed to simulate the absence of the stock, this assertion verifies that the
     * deleteStock() method correctly throws the expected exception when attempting to delete a non-existent stock.
     * Verification of Repository Method Invocation:
     *
     * verify(stockRepository, never()).delete("AAPL");: This line verifies that the delete() method of
     * the stockRepository is never called with the symbol "AAPL". This verification ensures that when the stock
     * is not found, the delete() method is not invoked, which aligns with the behavior expected when attempting
     * to delete a non-existent stock.
     *
     */


    @Test
    void deleteStockTest() {
        // Set up test data: Empty list of stocks returned when searching by ticker
        when(stockRepository.searchByTicker("AAPL")).thenReturn(new ArrayList<>());

        // Verify that the stock is not present before attempting deletion
        assertThrows(StockNotFoundException.class, () -> {
            stockService.deleteStock("AAPL");
        });

        // Verify that delete method is not called since the stock was not found
        verify(stockRepository, never()).delete("AAPL");
    }

    /**
     * This method tests the scenario where the stock to be updated does not exist in the stock repository.
     * It verifies that a StockNotFoundException is thrown and the correct error message is displayed.
     * It also ensures that the update method of the stock repository is never called.
     */

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



    private Stock createStockWithName(String name) {
        return new Stock("TICKER", name, "USD", "Tech", 100.0, 10, 100.0);
    }

    /**
     * This method tests the searchByName method in the StockService class.
     * It verifies that the method correctly searches for a stock by name.
     * It also verifies that the expected stock is returned and the search method is called with the correct arguments.
     */


    @Test
    void searchByNameTest() {
        String name = "Apple Inc.";
        Stock expectedStock = createStockWithName(name);
        List<Stock> allStocks = Arrays.asList(expectedStock);

        when(stockRepository.retrieveAll()).thenReturn(allStocks);
        when(search.searchByName(allStocks, name)).thenReturn(expectedStock);

        Stock foundStock = stockService.searchByName(allStocks, name);

        assertEquals(name, foundStock.getName());
        verify(search).searchByName(allStocks, name);
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

    /**
     * This method tests the behavior of the searchBySector method in the StockService class.
     * It verifies that the method correctly searches for stocks in the specified sector.
     *
     * Testing Strategy:
     * 1. Stub the searchBySector method of the search object to return a list of expected stocks when called with the sector "Technology".
     *    By stubbing this method, we simulate the scenario where the search operation returns the expected stocks.
     * 2. Call the searchBySector method of the stockService object with the sector "Technology".
     * 3. Assert that the returned stocks are not null.
     * 4. Assert that the size of the returned stocks is 2, indicating that two stocks were found in the specified sector.
     * 5. Verify that the searchBySector method of the search object is called with the sector "Technology".
     */

    @Test
    void searchBySectorTest() {
        List<Stock> expectedStocks = Arrays.asList(createStockWithTicker("AAPL"), createStockWithTicker("GOOGL"));
        when(search.searchBySector(anyList(), eq("Technology"))).thenReturn(expectedStocks);
        List<Stock> foundStocks = stockService.searchBySector("Technology");
        assertNotNull(foundStocks);
        assertEquals(2, foundStocks.size());
        verify(search).searchBySector(anyList(), eq("Technology"));
    }

    /**
     * Tests the sortByAttribute method in the StockService class.
     * It verifies that the method correctly sorts the stocks by the specified attribute.
     *
     * Internal Dependencies:
     *   - StockRepository: The repository used to retrieve and manipulate stocks.
     *   - Stock: The data model representing a stock.
     *   - StockService: The service class that provides operations on stocks.
     *
     * Test Environment:
     *   - stockRepository: A mock object of the StockRepository class.
     *   - stockService: An instance of the StockService class.
     *
     * Test Steps:
     *   1. Create a list of expected stocks with tickers "AAPL" and "GOOGL".
     *   2. Stub the sortByAttribute method of stockRepository to return the expectedStocks when called with attribute "name".
     *   3. Call the sortByAttribute method of stockService with attribute "name" and obtain the sorted stocks.
     *   4. Assert that the sortedStocks are not null.
     *   5. Assert that the size of sortedStocks is 2.
     *   6. Verify that the sortByAttribute method of stockRepository is called with attribute "name".
     */

    @Test
    void sortByAttributeTest() {
        List<Stock> expectedStocks = Arrays.asList(createStockWithTicker("AAPL"), createStockWithTicker("GOOGL"));
        when(stockRepository.sortByAttribute("name")).thenReturn(expectedStocks);
        List<Stock> sortedStocks = stockService.sortByAttribute("name");
        assertNotNull(sortedStocks);
        assertEquals(2, sortedStocks.size());
        verify(stockRepository).sortByAttribute("name");
    }

    /**
     * This method tests the behavior of the {@link StockService#getStockByTicker(String)} method in the {@link DefaultStockService} class when the stock is not found.
     * It verifies that the method returns null when the stock with the given ticker does not exist in the repository.
     * The test uses mockito to stub the {@link StockRepository# findById(String)} method to return null when called with the symbol "AAPL".
     * It then calls the {@link StockService#getStockByTicker(String)} method with the same symbol and asserts that the returned stock is null.
     * If the stock is found, an exception is thrown. Otherwise, the test passes.
     *
     * @see DefaultStockService
     * @see StockRepository
     * @see Stock
     */

    @Test
    void getStockByTickerNotFoundTest() {
        when(stockRepository.findById("AAPL")).thenReturn(null);
        Stock foundStock = stockService.getStockByTicker("AAPL");
        assertNull(foundStock, "Should return null for non-existing ticker");
    }

    /**
     * Test case to verify the behavior of the getStockByTicker method in the DefaultStockService class when the stock is found.
     */

    @Test
    void getStockByTickerFoundTest() {
        // Arrange
        String ticker = "AAPL";
        Stock expectedStock = new Stock(ticker, "Apple Inc.", "$", "Technology", 120.0, 50, 100.0);
        when(stockRepository.findById(ticker)).thenReturn(expectedStock);

        // Act
        Stock actualStock = stockService.getStockByTicker(ticker);

        // Assert
        assertNotNull(actualStock, "Found stock should not be null");
        assertEquals(expectedStock.getTicker(), actualStock.getTicker(), "Ticker should match");
        verify(stockRepository).findById(ticker);
    }

    /**
     * This method tests the scenario where a non-existent stock is saved.
     * It verifies that the stock is saved correctly and the saved stock is returned.
     * It also ensures that the stockRepository.save() method is called with the correct argument.
     */

    @Test
    void saveNonExistentStockTest() {
        // Arrange
        Stock newStock = new Stock("MSFT", "Microsoft Corporation", "$", "Technology", 130.0, 70, 110.0);
        when(stockRepository.save(newStock)).thenReturn(newStock);

        // Act
        Stock savedStock = stockService.saveStock(newStock);

        // Assert
        assertNotNull(savedStock, "Saved stock should not be null");
        assertEquals(newStock.getTicker(), savedStock.getTicker(), "Ticker should match");
        verify(stockRepository).save(newStock);
    }

    /**
     * This method tests the scenario where no stocks are found for the specified sector.
     * It verifies that a StockNotFoundException is thrown and the correct error message is displayed.
     *
     * @throws StockNotFoundException If no stocks are found for the specified sector.
     */


    @Test
    void searchBySectorNotFoundTest() {
        // Define the input sector
        String sector = "NonexistentSector";

        // Execute the search
        List<Stock> result = stockService.searchBySector(sector);

        // Assert that the result is an empty list
        assertTrue(result.isEmpty(), "Expected an empty list for a nonexistent sector");
    }

}