package com.cbfacademy.apiassessment.search;

import com.cbfacademy.apiassessment.stock.Stock;

import java.util.List;

public interface Search {
    Stock searchByName(List<Stock> stocks, String targetName);
    List<Stock> searchBySector(List<Stock> stocks, String sector);


}
