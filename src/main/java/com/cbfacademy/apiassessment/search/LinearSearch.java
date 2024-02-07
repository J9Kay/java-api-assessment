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
     * @param targetName The ticker symbol of the Stock to find.
     * @return The Stock object with the matching ticker, or null if not found.
     */
    @Override
    //Searches through a list of Stock objects for a stock that has a name matching the target name
    public Stock searchByName(List<Stock> stocks, String targetName) {
        // for - each loop to iterate over each stock object in the List<Stock>
        for (Stock stock : stocks) {
            // the search is case-insensitive as indicated by the use of equalsIgnoreCase
            if (stock.getName().equalsIgnoreCase(targetName)) {
                //if a matching stock is found return immediately
                return stock; // Stock with matching ticker found
            }
        }
        // if no matching stock is found after iterating over the enter list return null
        return null; // Stock not found
    }

    /**
     * Searches the given list of stocks for stocks in the specified sector.
     *
     * @param stocks The list of stocks to search through.
     * @param sector The sector to filter the stocks by.
     * @return A list of stocks that belong to the specified sector.
     */
    @Override
    //Searches through a list of Stock objects for a stock that has a name matching the target sector
    public List<Stock> searchBySector(List<Stock> stocks, String sector) {
        //create an Array list to store any results
        List<Stock> foundStocks = new ArrayList<>();
        // for - each loop to iterate over each stock object in the List<Stock>
        for (Stock stock : stocks) {
            // the search is case-insensitive as indicated by the use of equalsIgnoreCase
            if (stock.getSector().equalsIgnoreCase(sector)) {
                //if a matching sector is found in the stock object is it is added to the array list
                foundStocks.add(stock);
            }
        }
        return foundStocks; // List of Stock with matching sector found
    }
}
