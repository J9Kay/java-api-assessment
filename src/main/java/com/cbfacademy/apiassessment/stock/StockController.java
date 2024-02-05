package com.cbfacademy.apiassessment.stock;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * The StockController class handles HTTP requests related to stocks.
 */
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        if (stocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<Stock> getStockByTicker(@PathVariable String ticker) {
        Stock stock = stockService.getStockByTicker(ticker);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
//
    @PostMapping
    public ResponseEntity<Stock> saveStock(@RequestBody Stock stock) {
        Stock savedStock = stockService.saveStock(stock);
        System.out.println(savedStock.getName());
        return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
    }



    @PutMapping("/{ticker}")
    public ResponseEntity<Stock> updateStock(@RequestBody Stock stock) {
        if (stock == null || stock.getTicker() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Stock updatedStock = stockService.updateStock(stock);
        if (updatedStock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedStock, HttpStatus.OK);
    }

    @DeleteMapping("/{ticker}")
    public ResponseEntity<Void> deleteStock(@PathVariable String ticker) {
        stockService.deleteStock(ticker);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //read documentation of rest parameter
    @GetMapping("/sort")
    public ResponseEntity<List<Stock>> sortStocks(@RequestParam String attribute) {
        try {
            List<Stock> sortedStocks = stockService.sortByAttribute(attribute);
            if (sortedStocks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sortedStocks, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/search/{ticker}")
    public ResponseEntity<Stock> searchStockByTicker(@PathVariable String ticker) {
        Stock stock = stockService.searchByTicker(stockService.getAllStocks(), ticker);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/searchBySector/{sector}")
    public ResponseEntity<List<Stock>> searchStockBySector(@PathVariable String sector) {
        List<Stock> stocks = stockService.searchBySector(sector);
        if (!stocks.isEmpty()) {
            return ResponseEntity.ok(stocks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
