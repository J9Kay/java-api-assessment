package com.cbfacademy.apiassessment.stock;

import java.util.*;

public interface StockService {
    /**
     * Retrieve a list of all Stocks.
     *
     * @return A list of all Stocks.
     */
    Map<String,Stock> getAllStocks();

    /**
     * Retrieve a Stock by its ticker.
     *
     * @param ticker The ticker of the Stock to retrieve.
     * @return The Stock with the specified ticker, or null if not found.
     */
   Stock getStockByTicker(String ticker);

    /**
     * Create a new Stock.
     *
     * @param stock The Stock object to create.
     * @return The created Stock.
     */
    Stock saveStock(Stock stock);

    /**
     * Update an existing Stock by its ticker.
     *
     * @param ticker         The ticker of the Stock to update.
     * @param updatedStock The updated Stock object.
     * @return The updated STOCK, or null if the ticker is not found.
     */
    Stock updateStock(String ticker, Stock updatedStock);

    /**
     * Delete an Stock by its ticker.
     *
     * @param ticker The ticker of the Stock to delete.
     */
    void deleteStock(String ticker);

}