package com.cbfacademy.apiassessment;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addStock(@RequestBody Stock stock) {
        try {
            stockService.addStock(stock);
            return ResponseEntity.ok("Stock added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding stock: " + e.getMessage());
        }
    }

    @GetMapping("/{ticker}")
    public ResponseEntity<Stock> getStock(@PathVariable String ticker) {
        try {
            Stock stock = stockService.getStock(ticker);
            return ResponseEntity.ok(stock);
        } catch (StockNotFoundException e) {
            return ResponseEntity.notFound().build();
        }



    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        if (stocks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(stocks);
    }



}


