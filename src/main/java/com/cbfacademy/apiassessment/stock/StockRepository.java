package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.core.Repository;

import java.util.List;
/**
 * The StockRepository interface defines the operations for managing Stocks in the system.
 * It provides methods for retrieving, saving, updating, and deleting Stock records.
 */
public interface StockRepository extends Repository<Stock,String > {

    /**
     * Searches for the Stock where the ticker matches the provided string.
     *
     * @param ticker the name of the ticker
     * @return the stock that that match the ticker
     */
    List<Stock> searchByTicker(String ticker);

    /**
     * Searches for Stocks where the sector name matches the provided string.
     *
     * @param sector the name of the sector of the stock
     * @return a list of Stocks that match the sector name
     */
    List<Stock> searchBySector(String sector);

    /**
     * Sorts stocks based on a specified attribute.
     *
     * @param attribute The attribute to sort by.
     * @return a list of sorted Stocks
     */
    List<Stock> sortByAttribute(String attribute);



}
