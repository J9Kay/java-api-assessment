package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The DefaultStockService class implements the StockService interface and provides methods for managing stocks.
 * It uses a StockRepository to retrieve, save, update, and delete stock records.
 */
@Service
public class DefaultStockService implements StockService {
    private final StockRepository stockRepository;
    private final Search search;

    @Autowired
    public DefaultStockService(StockRepository stockRepository, Search search) {
        this.stockRepository = stockRepository;
        this.search = search;
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

    @Override
    public List<Stock> sortByAttribute(String attribute) {
        return stockRepository.sortByAttribute(attribute);

    }

    @Override
    public Stock searchByTicker(List<Stock> stocks, String targetTicker) {
        return search.searchByTicker(stocks, targetTicker);
    }

}
