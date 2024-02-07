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
     * @param updatedStock The updated Stock object.
     * @return The updated STOCK, or null if the ticker is not found.
     */
    Stock updateStock(Stock updatedStock);

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

    /**
     * Retrieves a stock by its name from the given list of stocks.
     *
     * @param stocks The list of stocks to search.
     * @param targetName The name of the stock to search for.
     * @return The stock with the specified name, or null if not found.
     */
    Stock searchByName(List<Stock> stocks, String targetName);

    /**
     * Searches for stocks by their sector.
     *
     * @param sector The sector of the stocks to search for.
     * @return A list of {@link Stock} objects matching the sector.
     */
    List<Stock> searchBySector(String sector);




}