package com.cbfacademy.apiassessment;
import java.util.HashMap;
import java.util.Map;
public class StockService {
    private Map<String, Stock> stockMap;

    public StockService() {
        stockMap = new HashMap<>();
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

    public void deleteStock(String ticker) {
        if (!stockMap.containsKey(ticker)) {
            throw new StockNotFoundException("Stock with ticker " + ticker + " not found");
        }
        stockMap.remove(ticker);
    }



}
