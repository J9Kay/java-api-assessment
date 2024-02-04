package com.cbfacademy.apiassessment.search;

import com.cbfacademy.apiassessment.stock.Stock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class LinearSearch implements Search {

    /**
     * Performs a linear search to find a Stock by its ticker.
     *
     * @param stocks The list of Stock objects to search through.
     * @param targetTicker The ticker symbol of the Stock to find.
     * @return The Stock object with the matching ticker, or null if not found.
     */
    @Override
    public Stock searchByTicker(List<Stock> stocks, String targetTicker) {
        for (Stock stock : stocks) {
            if (stock.getTicker().equalsIgnoreCase(targetTicker)) {
                return stock; // Stock with matching ticker found
            }
        }
        return null; // Stock not found
    }

    @Override
    public List<Stock> searchBySector(List<Stock> stocks, String sector) {
        List<Stock> foundStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getSector().equalsIgnoreCase(sector)) {
                foundStocks.add(stock);
            }
        }
        return foundStocks;
    }
}
