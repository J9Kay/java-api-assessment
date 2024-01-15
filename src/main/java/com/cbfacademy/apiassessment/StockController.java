package com.cbfacademy.apiassessment;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

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


}


