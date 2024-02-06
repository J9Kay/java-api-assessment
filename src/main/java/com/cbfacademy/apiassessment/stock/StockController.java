package com.cbfacademy.apiassessment.stock;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.Arrays;
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

            try {
                List<Stock> stocks = stockService.getAllStocks();
                if (stocks.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(stocks, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }


    @GetMapping("/{ticker}")
    @Operation(summary = "Retrieve a stock", description = "Retrieves a stock available in the system",
            responses = {
                    @ApiResponse(description = "Successful retrieval", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Stock not found", responseCode = "404")
            })
    public ResponseEntity<Stock> getStockByTicker(@PathVariable String ticker) {
        Stock stock = stockService.getStockByTicker(ticker);
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
//a
    @PostMapping
    @Operation(summary = "Add a stock or stocks", description = "Add a stock or stocks to the system",
            responses = {
                    @ApiResponse(description = "Successful creation of a stock", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Validation error", responseCode = "400"),
                    @ApiResponse(description = "Error adding stock", responseCode = "404"),
                    @ApiResponse(description = "Internal server error", responseCode = "500")

            })
    public ResponseEntity<Object> saveStock(@RequestBody Stock stock) {
        if (stockService.searchByTicker(stockService.getAllStocks(), stock.getTicker()) != null) {
        return new ResponseEntity<>(Map.of("error", "Stock with ticker " + stock.getTicker() + " already exists."), HttpStatus.BAD_REQUEST);
    }
        List<String> validationErrors = validateStock(stock);
        if (!validationErrors.isEmpty()) {
            return new ResponseEntity<>(Map.of("errors", validationErrors), HttpStatus.BAD_REQUEST);
        }
        try {
            Stock savedStock = stockService.saveStock(stock);
            return new ResponseEntity<>(savedStock, HttpStatus.CREATED);
        } catch (StockNotFoundException e) {
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "An unexpected error occurred while saving the stock. Error: "+e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //helper function to check for errors
    private List<String> validateStock(Stock stock) {
        List<String> errors = new ArrayList<>();
        if (StringUtils.isEmpty(stock.getName())) {
            errors.add("Stock name is required.");
        }
        if (stock.getCurrentPrice() < 0) {
            errors.add("Current price cannot be negative.");
        }
        if (stock.getPurchasePrice() < 0) {
            errors.add("Purchase price cannot be negative.");
        }
        if (stock.getQuantity() < 0) {
            errors.add("Quantity cannot be negative.");
        }
        // I can add more errors as needed
        return errors;
    }



    @PutMapping("/{ticker}")
    @Operation(summary = "Update stock information", description = "Update information on an existing stock available in the system",
            responses = {
                    @ApiResponse(description = "Successful update", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Error updating stock", responseCode = "404")
            })
    public ResponseEntity<Stock> updateStock(@RequestBody Stock stock) {

        if (stock == null || stock.getTicker() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Stock updatedStock = stockService.updateStock(stock);
            if (updatedStock == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(updatedStock, HttpStatus.OK);
        } catch (StockNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{ticker}")
    @Operation(summary = "Delete a stock", description = "Delete a stock available in the system",
            responses = {
                    @ApiResponse(description = "Successful deletion", responseCode = "204",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Error deleting stock", responseCode = "404")
            })
    public ResponseEntity<?> deleteStock(@PathVariable String ticker) {
        try {
            stockService.deleteStock(ticker);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch(StockNotFoundException e) {
            // Handle the case where the stock to be deleted does not exist
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other unexpected exceptions
            return new ResponseEntity<>(Map.of("error", "Internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //read documentation of rest parameter
    @GetMapping("/sort")
    @Operation(summary = "Sort stock based on a specified attribute", description = "This endpoint allows users to," +
            " organize their stock view according to various stock attributes such as name, price, or quantity." +
            " It improves user experience by providing a customized view.",
            responses = {
                    @ApiResponse(description = "Sort successful", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Error sorting by attribute", responseCode = "404")
            })
    public ResponseEntity<List<Stock>> sortStocks(@RequestParam String attribute) {
        // List of valid attributes that you can sort by
        List<String> validAttributes = Arrays.asList("name", "currentPrice", "purchasePrice", "quantity");
        if (!validAttributes.contains(attribute)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
//    @GetMapping("/search/{ticker}")  // this one is redundant to be changed to name.
//    @Operation(summary = "Searches for a stock by its ticker symbol.", description = "This function is crucial for" +
//            " users looking to quickly find detailed information about a specific stock, including its current price," +
//            " quantity owned, and purchase price.",
//            responses = {
//                    @ApiResponse(description = "Filter successful", responseCode = "200",
//                            content = @Content(schema = @Schema(implementation = Stock.class))),
//                    @ApiResponse(description = "Filter unsuccessful", responseCode = "404")
//            })
//    public ResponseEntity<Stock> searchStockByTicker(@PathVariable String ticker) {
//        Stock stock = stockService.searchByTicker(stockService.getAllStocks(), ticker);
//        if (stock != null) {
//            return ResponseEntity.ok(stock);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/searchBySector/{sector}")
    @Operation(summary = "Filters stocks by their sector", description = "This endpoint is designed for users" +
            " interested in viewing stocks within a specific market sector.",
            responses = {
                    @ApiResponse(description = "Filter successful", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Filter unsuccessful", responseCode = "404")
            })
    public ResponseEntity<List<Stock>> searchStockBySector(@PathVariable String sector) {
        List<Stock> stocks = stockService.searchBySector(sector);
        if (!stocks.isEmpty()) {
            return ResponseEntity.ok(stocks);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
