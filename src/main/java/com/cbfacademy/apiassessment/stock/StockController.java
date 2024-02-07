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

    /**
     * The stockService variable represents an instance of the StockService interface, which provides methods for managing stocks.
     */

    private final StockService stockService;

    /**
     * The StockController class is responsible for handling HTTP requests related to managing stocks.
     */

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * Retrieves a list of all stocks available in the system.
     *
     * @return ResponseEntity object containing the list of stocks
     *         - HttpStatus.OK (200): Successful retrieval
     *         - HttpStatus.NO_CONTENT (204): No stocks found
     *         - HttpStatus.INTERNAL_SERVER_ERROR (500): Error occurred during retrieval
     */


    @GetMapping
    @Operation(summary = "Get all stocks", description = "Retrieves a list of all stocks available in the system",
            responses = {
                    @ApiResponse(description = "Successful retrieval", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "No stocks found", responseCode = "404")
            })
    public ResponseEntity<List<Stock>> getAllStocks() {
        //try and catch block to check that the stock object is not empty and handle and exceptions

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

    /**
     * Retrieves a stock by its ticker symbol.
     *
     * @param ticker The ticker symbol of the stock to retrieve.
     * @return ResponseEntity object containing the retrieved stock.
     *         Returns HttpStatus.OK (200) upon successful retrieval.
     *         Returns HttpStatus.NOT_FOUND (404) if the stock is not found.
     */


    @GetMapping("/{ticker}")
    @Operation(summary = "Retrieve a stock", description = "Retrieves a stock available in the system",
            responses = {
                    @ApiResponse(description = "Successful retrieval", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Stock not found", responseCode = "404")
            })
    public ResponseEntity<Stock> getStockByTicker(@PathVariable String ticker) {
        Stock stock = stockService.getStockByTicker(ticker);
        //check if stock is in system
        if (stock == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    /**
     * Saves a stock or stocks to the system.
     *
     * @param stock The stock or stocks to be saved.
     * @return ResponseEntity object containing the saved stock or an error message.
     *         Returns HttpStatus.CREATED (201) if the stock is successfully saved.
     *         Returns HttpStatus.BAD_REQUEST (400) if the stock with the same ticker already exists
     *         or if there are validation errors in the stock data.
     *         Returns HttpStatus.NOT_FOUND (404) if there is an error adding the stock.
     *         Returns HttpStatus.INTERNAL_SERVER_ERROR (500) if an unexpected error occurs while saving the stock.
     */

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
        if (stockService.getStockByTicker(stock.getTicker()) != null) {
            return new ResponseEntity<>(Map.of("error", "Stock with ticker " + stock.getTicker() +
                    " already exists."), HttpStatus.BAD_REQUEST);
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

    /**
     * This method validates the stock data and checks for any errors.
     *
     * @param stock The stock to be validated.
     * @return A list of error messages. Returns an empty list if no errors are found.
     */

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


    /**
     * Updates the information of an existing stock in the system.
     *
     * @param stock The Stock object containing the updated information.
     * @return ResponseEntity object containing the updated stock or an error message.
     *         Returns HttpStatus.OK (200) if the stock is successfully updated.
     *         Returns HttpStatus.NOT_FOUND (404) if the stock is not found.
     *         Returns HttpStatus.BAD_REQUEST (400) if the stock or its ticker is null.
     *         Returns HttpStatus.INTERNAL_SERVER_ERROR (500) if an unexpected error occurs.
     */

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

    /**
     * Deletes a stock from the system based on the given ticker symbol.
     *
     * @param ticker The ticker symbol of the stock to be deleted.
     * @return A ResponseEntity object.
     *         Returns HttpStatus.NO_CONTENT (204) upon successful deletion.
     *         Returns HttpStatus.NOT_FOUND (404) if the stock to be deleted does not exist.
     *         Returns HttpStatus.INTERNAL_SERVER_ERROR (500) if an unexpected error occurs.
     */

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

    /**
     * Sorts stocks based on a specified attribute.
     *
     * @param attribute The attribute by which to sort the stocks. Valid attributes are: name, currentPrice,
     *                  purchasePrice, quantity.
     * @return A ResponseEntity object containing the sorted stocks. Returns HttpStatus.OK (200) if the sorting
     *         is successful. Returns HttpStatus.BAD_REQUEST (400) if the attribute is not valid or if there
     *         is an error sorting the stocks. Returns HttpStatus.NO_CONTENT (204) if no stocks are found.
     *         Returns HttpStatus.INTERNAL_SERVER_ERROR (500) if an unexpected error occurs.
     */
    @GetMapping("/sort")
    @Operation(summary = "Sort stock based on a specified attribute", description = "This endpoint allows users to," +
            " organize their stock view according to various stock attributes such as name, price, or quantity." +
            " It improves user experience by providing a customized view.",
            responses = {
                    @ApiResponse(description = "Sort successful", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Attribute not valid, stocks can be sorted by either: name, currentPrice, purchasePrice or quantity", responseCode = "400"),
                    @ApiResponse(description = "Error sorting by attribute", responseCode = "404")
            })
    public ResponseEntity<Object> sortStocks(@RequestParam String attribute) {
        // List of valid attributes that you can sort by
        List<String> validAttributes = Arrays.asList("name", "currentPrice", "purchasePrice", "quantity");
        if (!validAttributes.contains(attribute)) {
            return new ResponseEntity<>("Attribute not valid, stocks can be sorted by either: name, currentPrice, purchasePrice or quantity", HttpStatus.BAD_REQUEST);
        }
        try {
            List<Stock> sortedStocks = stockService.sortByAttribute(attribute);
            if (sortedStocks.isEmpty()) {
                return new ResponseEntity<>("No stocks found", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(sortedStocks, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Error sorting by attribute", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Searches for a stock by its name.
     *
     * @param name The name of the stock to search for.
     * @return A ResponseEntity object containing the found stock.
     *         Returns HttpStatus.OK (200) if the stock is found.
     *         Returns HttpStatus.NOT_FOUND (404) if the stock is not found.
     */
    @GetMapping("/search/{name}")
    @Operation(summary = "Searches for a stock by its name.", description = "This function is crucial for" +
            " users looking to quickly find a specific stock",
            responses = {
                    @ApiResponse(description = "Filter successful", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Stock.class))),
                    @ApiResponse(description = "Filter unsuccessful", responseCode = "404")
            })
    public ResponseEntity<Stock> searchStockByName(@PathVariable String name) {
        Stock stock = stockService.searchByName(stockService.getAllStocks(), name);
        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Searches for stocks by their sector.
     *
     * @param sector The sector of the stocks to search for.
     * @return A ResponseEntity object containing a list of stocks matching the sector.
     *         Returns HttpStatus.OK (200) if the search is successful and stocks are found.
     *         Returns HttpStatus.NOT_FOUND (404) if no stocks are found for the sector.
     */

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
