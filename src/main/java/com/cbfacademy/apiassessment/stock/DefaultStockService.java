package com.cbfacademy.apiassessment.stock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DefaultStockService implements StockService {
    private final StockRepository stockRepository;

    @Autowired
    public DefaultStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Map<String, Stock> getAllStocks() {
        List<Stock> stocks = stockRepository.retrieveAll();
        Map<String, Stock> stocksMap = new HashMap<>();
        for (Stock s : stocks) {
            stocksMap.put(s.getTicker(), s);
        }
        return stocksMap;
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
