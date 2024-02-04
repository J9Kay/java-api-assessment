package com.cbfacademy.apiassessment.search;

import com.cbfacademy.apiassessment.stock.Stock;

import java.util.List;

public interface Search {
    Stock searchByTicker(List<Stock> stocks, String targetTicker);
    List<Stock> searchBySector(List<Stock> stocks, String sector);


}
