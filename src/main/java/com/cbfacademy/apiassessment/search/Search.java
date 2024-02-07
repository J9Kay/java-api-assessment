package com.cbfacademy.apiassessment.search;

import com.cbfacademy.apiassessment.stock.Stock;

import java.util.List;

/**
 * The Search interface provides methods to search for stocks based on different criteria.
 */
public interface Search {
    // Method to search for stock by name
    Stock searchByName(List<Stock> stocks, String targetName);
    //Method to search for a stock by sector
    List<Stock> searchBySector(List<Stock> stocks, String sector);


}
