package com.cbfacademy.apiassessment.stock;

import java.util.*;

/**
 * The StockService interface provides methods for managing stocks.
 */
public interface StockService {
    /**
     * Retrieve a list of all Stocks.
     *
     * @return A list of all Stocks.
     */
    List<Stock> getAllStocks();

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

    /**
     * Sorts the stocks based on a specified attribute.
     *
     * @param attribute The attribute by which to sort the stocks.
     * @return A sorted list of stocks based on the specified attribute.
     */
    List<Stock> sortByAttribute(String attribute);

    Stock searchByTicker(List<Stock> stocks, String targetTicker);
    //validate the valude being sorted is one that can be sorted by


    List<Stock> searchBySector(String sector);




}