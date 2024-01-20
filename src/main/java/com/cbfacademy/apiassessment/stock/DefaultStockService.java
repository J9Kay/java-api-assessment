package com.cbfacademy.apiassessment.stock;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultStockService implements StockService {

    private final Map<String, Stock> stockMap = new HashMap<>();

    @Override
    public Map<String, Stock> getAllStocks() {
        return stockMap;
    }

    @Override
    public Stock getStockByTicker(String ticker) {
        return stockMap.get(ticker);
    }

    @Override
    public Stock saveStock(Stock stock) {
        if (stock == null || stock.getTicker() == null) {
            // Handle this scenario. For example, throw an exception or log a warning.
            throw new IllegalArgumentException("Stock or its ticker cannot be null.");
        }
        stockMap.put(stock.getTicker(), stock);
        return stock;
    }

    @Override
    public Stock updateStock(String ticker, Stock updatedStock) {
        if (ticker == null || updatedStock == null) {
            // Handle this scenario.
            throw new IllegalArgumentException("Ticker and stock to be updated cannot be null.");
        }
        if (!stockMap.containsKey(ticker)) {
            return null; // Stock does not exist.
        }
        stockMap.put(ticker, updatedStock);
        return updatedStock;
    }



    @Override
    public void deleteStock(String ticker) {
        if (ticker == null) {
            // Handle null ticker.
            throw new IllegalArgumentException("Stock ticker cannot be null.");
        }
        stockMap.remove(ticker);
    }

}
