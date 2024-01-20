//package com.cbfacademy.apiassessment.stock;
//import org.junit.jupiter.api.*;
//import static org.junit.jupiter.api.Assertions.*;
//import java.util.List;
//public class JsonStockRepositoryTests {
//
//    private JsonStockRepository repository;
//
//    @BeforeEach
//    void setUp() {
//        // Assuming your JsonStockRepository takes a file path as a constructor parameter
//        repository = new JsonStockRepository("src/test/resources/test-stocks.json");
//    }
//
//    @Test
//    void testLoadDataFromJson() {
//        List<Stock> stocks = repository.retrieveAll();
//        assertFalse(stocks.isEmpty(), "Stocks should not be empty after loading data from JSON");
//    }
//
//    @Test
//    void testSaveAndFindById() {
//        Stock newStock = new Stock("AAPL", "New Corp", "$", "Tech", 100.0, 5, 95.0);
//        repository.save(newStock);
//
//        Stock retrievedStock = repository.findById("AAPL");
//        assertNotNull(retrievedStock, "Newly saved stock should be retrievable");
//        assertEquals("New Corp", retrievedStock.getName(), "Stock name should match");
//    }
//
//    @Test
//    void testDelete() {
//        repository.delete("AAPL");
//        assertNull(repository.findById("AAPL"), "Deleted stock should not be retrievable");
//    }
//
//    // Add more tests as needed to cover update, search by ticker, and search by sector
//}
