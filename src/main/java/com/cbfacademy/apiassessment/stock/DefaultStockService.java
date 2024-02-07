package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.cbfacademy.apiassessment.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The DefaultStockService class implements the StockService interface and provides methods for managing stocks.
 * It uses a StockRepository to retrieve, save, update, and delete stock records.
 */
@Service
public class DefaultStockService implements StockService {
    /**
     * log is a private static final Logger variable. It is used for logging messages and events in the DefaultStockService class.
     *
     * The LoggerFactory.getLogger(DefaultStockService.class) method returns an instance of the Logger class, which is used to perform logging operations.
     * The getLogger() method takes the class name DefaultStockService.class as its argument and returns a Logger instance with the specified name.
     *
     * The log variable is declared as private, which means it can only be accessed within the same class (DefaultStockService).
     * The static modifier indicates that the log variable belongs to the class itself, not to an instance of the class.
     * The final modifier indicates that the log variable cannot be reassigned once it is initialized.
     *
     * Example usage:
     * log.debug("This is a debug message");
     * log.info("This is an info message");
     * log.warn("This is a warning message");
     * log.error("This is an error message");
     */
    private static final Logger log = LoggerFactory.getLogger(DefaultStockService.class);

    /**
     * The stockRepository variable represents a repository for managing stocks in the system.
     * It is of type StockRepository, which is an interface that defines the operations for managing Stocks.
     * The StockRepository interface provides methods for retrieving, saving, updating, and deleting Stock records.
     * This variable is declared as private final, indicating that it is a constant field and cannot be modified once
     * initialized.
     */

    private final StockRepository stockRepository;

    /**
     * The Search interface provides methods to search for stocks based on different criteria.
     */
    private final Search search;

    /**
     * The DefaultStockService class is a implementation of the StockService interface.
     * It provides methods for managing stocks in the system.
     *
     * @param stockRepository The repository for managing stocks.
     * @param search The search interface used for searching stocks.
     */
    @Autowired
    public DefaultStockService(StockRepository stockRepository, Search search) {
        this.stockRepository = stockRepository;
        this.search = search;
    }

    /**
     * Retrieve a list of all Stocks.
     *
     * @return A list of all Stocks.
     * @throws Exception if there is an error while retrieving the stocks.
     */

    @Override
    public List<Stock> getAllStocks() {
        try {
            return stockRepository.retrieveAll();
        } catch (Exception e) {
            log.error("Failed to retrieve all stocks", e);
            throw e;
        }
    }

    /**
     * Retrieves a stock by its ticker.
     *
     * @param ticker The ticker of the stock to retrieve.
     * @return The stock with the specified ticker, or null if not found.
     * @throws Exception if there is an error while retrieving the stock.
     */

    @Override
    public Stock getStockByTicker(String ticker) {
        try {
            return stockRepository.findById(ticker);
        } catch (Exception e) {
            log.error("Failed to find stock with ticker: {}", ticker, e);
            throw e;
        }
    }

    /**
     * Saves the given Stock object in the repository.
     *
     * @param stock The Stock object to create.
     * @return The saved Stock object.
     * @throws DuplicateStockException if the stock with the same ticker already exists in the repository.
     * @throws PersistenceException if there is an error while saving the stock.
     */

    @Override
    public Stock saveStock(Stock stock) {
        try {
            // Check if the stock already exists in the repository
            Stock existingStock = stockRepository.findById(stock.getTicker());
            if (existingStock != null) {
                // Stock with the same ticker already exists
                log.error("Stock with ticker {} already exists", stock.getTicker());
                throw new DuplicateStockException("Stock with ticker " + stock.getTicker() + " already exists.");
            }
            // If it doesn't exist, proceed with saving the new stock
            return stockRepository.save(stock);
        } catch (DuplicateStockException e) {
            // Handle the case where the stock already exists
            log.error("You Attempted to save duplicate stock: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error saving stock: {}", stock.getTicker(), e);
            throw new PersistenceException("Failed to save stock", e);
        }
    }

    /**
     * Updates the stock in the system with the provided updatedStock object.
     *
     * @param updatedStock The updated Stock object.
     * @return The updated Stock object.
     * @throws IllegalArgumentException If the updatedStock parameter is null or if the ticker of updatedStock is null.
     * @throws StockNotFoundException If the stock with the specified ticker is not found in the system.
     * @throws PersistenceException If there is an error while updating the stock in the system.
     */



    @Override
    public Stock updateStock(Stock updatedStock) {
        if (updatedStock == null || updatedStock.getTicker() == null) {
            log.error("Stock object or ticker is null");
            throw new IllegalArgumentException("Stock and its ticker must not be null");
        }
        try {
            Stock existingStock = stockRepository.findById(updatedStock.getTicker());
            if (existingStock == null) {
                throw new StockNotFoundException("Stock with ticker " + updatedStock.getTicker() + " not found");
            }
            return stockRepository.update(updatedStock);
        } catch (StockNotFoundException e) {
            log.error("Failed to update stock because it was not found: {}", e.getMessage());
            throw e; // Re-throw the exception to be handled further up the call stack if necessary
        } catch (Exception e) {
            log.error("Unexpected error updating stock: {}", updatedStock.getTicker(), e);
            throw new PersistenceException("Failed to update stock due to an unexpected error", e);
        }
    }

    /**
     * Deletes a stock from the system based on its ticker.
     *
     * @param ticker The ticker of the stock to delete.
     * @throws StockNotFoundException If the stock with the specified ticker is not found in the system.
     * @throws PersistenceException If there is an error while deleting the stock from the system.
     */



    public void deleteStock(String ticker) {
        // Check if the stock exists
        List<Stock> stocks = stockRepository.searchByTicker(ticker);
        if (stocks.isEmpty()) {
            throw new StockNotFoundException("Stock not found: " + ticker);
        }
        // Attempt to delete the stock
        try {
            stockRepository.delete(ticker);
        } catch (Exception e) {
            log.error("Error deleting stock: {}", ticker, e);
            throw new PersistenceException("Failed to delete stock: " + ticker, e);
        }
    }

    /**
     * Sorts the stocks based on the specified attribute.
     *
     * @param attribute The attribute by which to sort the stocks.
     * @return A list of sorted stocks.
     * @throws PersistenceException If there is an error while sorting the stocks.
     */

    @Override
    public List<Stock> sortByAttribute(String attribute) {
        try {
            return stockRepository.sortByAttribute(attribute);
        } catch (Exception e) {
            log.error("Error sorting stocks by attribute: {}", attribute, e);
            throw new PersistenceException("Failed to sort stocks", e);
        }
    }

    /**
     * Searches for a stock by name in a given list of stocks.
     *
     * @param stocks The list of stocks to search in.
     * @param targetName The name of the stock to search for.
     * @return The found stock with the specified name, or null if not found.
     * @throws PersistenceException If there is an error while searching for the stock.
     */

    @Override
    public Stock searchByName(List<Stock> stocks, String targetName) {
        try {
            return search.searchByName(stockRepository.retrieveAll(), targetName);
        } catch (Exception e) {
            log.error("Error searching stock by name: {}", targetName, e);
            throw new PersistenceException("Failed to search stock by name", e);
        }
    }

    /**
     * Search for stocks by sector.
     *
     * @param sector The sector to search for.
     * @return A list of stocks that belong to the specified sector.
     * @throws PersistenceException If there is an error while searching for stocks by sector.
     */

    @Override
    public List<Stock> searchBySector(String sector) {
        List<Stock> allStocks;
        try {
            allStocks = getAllStocks();
        } catch (Exception e) {
            log.error("Error retrieving all stocks", e);
            throw new PersistenceException("Failed to retrieve all stocks", e);
        }

        List<Stock> stocksInSector = search.searchBySector(allStocks, sector);
        if (stocksInSector.isEmpty()) {
        }
        return stocksInSector;
    }


}
