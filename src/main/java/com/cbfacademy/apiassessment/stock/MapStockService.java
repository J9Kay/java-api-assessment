package com.cbfacademy.apiassessment.stock;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class MapStockService implements StockService {

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
        stockMap.put(stock.getTicker(), stock);
        return stock;
    }

    @Override
    public Stock updateStock(String ticker, Stock updatedStock) {
        if (!stockMap.containsKey(ticker)) {
            return null;
        }
        stockMap.put(ticker, updatedStock);
        return updatedStock;
    }

    @Override
    public void deleteStock(String ticker) {
        stockMap.remove(ticker);
    }
}
