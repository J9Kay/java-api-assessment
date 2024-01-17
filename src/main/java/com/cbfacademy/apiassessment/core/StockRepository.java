package com.cbfacademy.apiassessment.core;

import com.cbfacademy.apiassessment.stock.Stock;

import java.io.Serializable;
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
     * Searches for Stocks where the industry name matches the provided string.
     *
     * @param industry the name of the industry of the stock
     * @return a list of Stocks that match the industry name
     */
    List<Stock> searchByIndustry(String industry);




}
