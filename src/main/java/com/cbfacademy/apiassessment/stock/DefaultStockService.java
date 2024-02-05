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
    private static final Logger log = LoggerFactory.getLogger(DefaultStockService.class);

    private final StockRepository stockRepository;
    private final Search search;

    @Autowired
    public DefaultStockService(StockRepository stockRepository, Search search) {
        this.stockRepository = stockRepository;
        this.search = search;
    }

    @Override
    public List<Stock> getAllStocks() {
        try {
            return stockRepository.retrieveAll();
        } catch (Exception e) {
            log.error("Failed to retrieve all stocks", e);
            throw e; // Re-throw the exception or handle it as needed
        }
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        try {
            return stockRepository.findById(ticker);
        } catch (Exception e) {
            log.error("Failed to find stock with ticker: {}", ticker, e);
            throw e; // Re-throw the exception or handle it as needed
        }
    }

    @Override
    public Stock saveStock(Stock stock) {
        try {
            return stockRepository.save(stock);
        } catch (Exception e) {
            log.error("Error saving stock: {}", stock.getTicker(), e);
            throw new PersistenceException("Failed to save stock", e);
        }
    }


    @Override
    public Stock updateStock(Stock updatedStock) {
        // Check if the updatedStock object or its ticker is null
        if (updatedStock == null || updatedStock.getTicker() == null) {
            log.error("Stock object or ticker is null");
            throw new IllegalArgumentException("Stock and its ticker must not be null");
        }
        try {
            return stockRepository.update(updatedStock);
        } catch (Exception e) {
            log.error("Error updating stock: {}", updatedStock.getTicker(), e);
            throw new PersistenceException("Failed to update stock", e);
        }
    }


    @Override
    public void deleteStock(String ticker) {
        try {
            stockRepository.delete(ticker);
        } catch (Exception e) {
            log.error("Error deleting stock: {}", ticker, e);
            throw new PersistenceException("Failed to delete stock", e);
        }
    }

    @Override
    public List<Stock> sortByAttribute(String attribute) {
        try {
            return stockRepository.sortByAttribute(attribute);
        } catch (Exception e) {
            log.error("Error sorting stocks by attribute: {}", attribute, e);
            throw new PersistenceException("Failed to sort stocks", e);
        }
    }

    @Override
    public Stock searchByTicker(List<Stock> stocks, String targetTicker) {
        try {
            return search.searchByTicker(stocks, targetTicker);
        } catch (Exception e) {
            log.error("Error searching stock by ticker: {}", targetTicker, e);
            throw new PersistenceException("Failed to search stock by ticker", e);
        }
    }

    @Override
    public List<Stock> searchBySector(String sector) {
        try {
            List<Stock> allStocks = getAllStocks();
            return search.searchBySector(allStocks, sector);
        } catch (Exception e) {
            log.error("Error searching stocks by sector: {}", sector, e);
            throw new PersistenceException("Failed to search stocks by sector", e);
        }
    }

}
