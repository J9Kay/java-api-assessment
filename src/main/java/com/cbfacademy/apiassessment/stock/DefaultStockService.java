package com.cbfacademy.apiassessment.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The DefaultStockService class implements the StockService interface and provides methods for managing stocks.
 * It uses a StockRepository to retrieve, save, update, and delete stock records.
 */
@Service
public class DefaultStockService implements StockService {
    private final StockRepository stockRepository;

    @Autowired
    public DefaultStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.retrieveAll();
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        return stockRepository.findById(ticker);
    }

    @Override
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock updateStock(String ticker, Stock updatedStock) {
        Stock stockToUpdate = getStockByTicker(ticker);
        if (stockToUpdate != null) {
            return stockRepository.update(updatedStock);
        } else {
            return null;
        }
    }

    @Override
    public void deleteStock(String ticker) {
        stockRepository.delete(ticker);
    }
}
