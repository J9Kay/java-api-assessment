package com.cbfacademy.apiassessment.stock;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Stocks")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    @GetMapping
    @Operation(summary = "Get all stocks", description = "Retrieves a list of all stocks available in the system",
            responses = {
                    @ApiResponse(description = "Successful retrieval", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "No stocks found", responseCode = "404")
            })
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        if (stocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }


    @Operation(
            description = "Get endpoint for retrieving stocks by ID",
            summary= "This is a summary for GET endpoint to retreive stock by ticker",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse (
                            description = "Unauthorised / Invalid token",
                            responseCode = "403"
                    )
            }
    )

    @GetMapping("/{ticker}")
    public ResponseEntity<Stock> getStockByTicker(@PathVariable String ticker) {
        Stock stock = stockService.getStockByTicker(ticker);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
//a
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
