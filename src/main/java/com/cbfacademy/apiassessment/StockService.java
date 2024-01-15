package com.cbfacademy.apiassessment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockService {
    private Map<String, Stock> stockMap;

    // store data so that it is unique and can be quickly retrieved
    public StockService() {
        stockMap = new HashMap<>();
    }

    public List<Stock> getAllStocks() {
        return new ArrayList<>(stockMap.values());
    }


    public void addStock(Stock stock) {
        stockMap.put(stock.getTicker(), stock);
    }

    public Stock getStock(String ticker) {
        if (!stockMap.containsKey(ticker)) {
            throw new StockNotFoundException("Stock with ticker " + ticker + " not found");
        }
        return stockMap.get(ticker);
    }

    public void updateStock(String ticker, Stock updatedStock) {
        if (!stockMap.containsKey(ticker)) {
            throw new StockNotFoundException("Stock with ticker " + ticker + " not found");
        }
        stockMap.put(ticker, updatedStock);
    }

    public void deleteStock(String ticker) {
        if (!stockMap.containsKey(ticker)) {
            throw new StockNotFoundException("Stock with ticker " + ticker + " not found");
        }
        stockMap.remove(ticker);
    }



}
