package com.cbfacademy.apiassessment.stock;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StockSorter {
    public static void sortBySector(List<Stock> stocks) {
        Collections.sort(stocks, Comparator.comparing(Stock::getSector));
    }
}
