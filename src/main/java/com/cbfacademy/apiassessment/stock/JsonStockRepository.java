package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import java.util.stream.Collectors;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * The JsonStockRepository class is an implementation of the StockRepository interface
 * that stores and retrieves stock data from a JSON file. It uses Jackson's ObjectMapper
 * for JSON serialization and deserialization.
 */
@Repository
public class JsonStockRepository implements StockRepository {
    /**
     * The filepath variable represents the file path of the JSON file used for persistence in the JsonStockRepository class.
     * It is a private final String type variable.
     * The filepath is injected from application properties. The value can be set through constructor injection.
     * It is used to load data from the JSON file and save data to the same file.
     * The JSON file path must be a valid file path in the system.
     */
    private final String filepath;
    /**
     * The objectMapper variable is an instance of the ObjectMapper class.
     * It is used for converting Java objects to JSON and vice versa.
     * The ObjectMapper class is provided by the Jackson library.
     * This variable is declared as private final, which means it is a constant and cannot be modified once initialized.
     *
     */
    private final ObjectMapper objectMapper;
    /**
     * The database variable is a private final Map object that holds stocks.
     * The key is a String representing the ticker of the stock,
     * and the value is an instance of the Stock class.
     * It is used to store and retrieve stock information in the system.
     */
    private final Map<String, Stock> database;
    /**
     * The resourceLoader variable represents a resource loader that is used to load resources from the classpath or file system.
     * It is an instance of the ResourceLoader interface.
     *
     * The resourceLoader is injected into the JsonStockRepository class using constructor injection,
     * and it is used in the loadDataFromJson() and saveDataToJson() methods.
     * In the loadDataFromJson() method, the resourceLoader is used to retrieve the resource object for the specified filepath.
     * If the resource file exists, it is parsed using the objectMapper to load the data into a map. If the resource
     * file does not exist, a PersistenceException is thrown.
     *
     * In the saveDataToJson() method, the resourceLoader is again used to retrieve the resource object for the specified filepath.
     * If the resource is a file, the data in the database is written to the resource file using the objectMapper.
     * If the resource is not a file, a PersistenceException is thrown.
     *
     * The resourceLoader variable is marked as final, meaning that once it is assigned a value, it cannot be reassigned.
     * This ensures that the resourceLoader used by the JsonStockRepository instance remains the same throughout its lifecycle.
     *
     * The resourceLoader variable is private, meaning that it can only be accessed within the JsonStockRepository class.
     * Other classes cannot directly access or modify the resourceLoader variable.
     */
    private final ResourceLoader resourceLoader;


    /**
     * The JsonStockRepository class is responsible for managing stock data using JSON files as the data source.
     * It implements the StockRepository interface and provides methods for retrieving, saving, updating, and deleting
     * stock records.
     */
    public JsonStockRepository(@Value("${json.file.path}") String filepath, ResourceLoader resourceLoader) {
        this.filepath = filepath;
        System.out.println("Filepath for JSON Respository: " + filepath);
        this.objectMapper = new ObjectMapper();
        this.resourceLoader = resourceLoader;
        this.database = loadDataFromJson();
    }

    /**
     * Loads data from a JSON file and returns the data as a map of stock objects.
     *
     * @return a map of stock objects loaded from the JSON file
     * @throws PersistenceException if there is an issue with loading the data from the JSON file
     */

    private Map<String, Stock> loadDataFromJson() {
        try {
            Resource resource = resourceLoader.getResource(filepath);
            if (resource.exists()) {
                return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
            } else {
                throw new PersistenceException("JSON file not found: " + filepath);
            }
        } catch (IOException e) {
            throw new PersistenceException("Failed to load data from JSON", e);
        }
    }

    /**
     * Saves the data to a JSON file.
     *
     * @throws PersistenceException if there is an issue with saving the data to the JSON file
     */

    private void saveDataToJson() {
        try {
            Resource resource = resourceLoader.getResource(filepath);
            if (resource.isFile()) {
                File file = resource.getFile();
                System.out.println("Saving data to JSON file at: " + file.getAbsolutePath());
                objectMapper.writeValue(resource.getFile(), database);
                System.out.println("Data successfully saved to JSON file.");
            } else {
                System.out.println("Cannot save to source: " +filepath);
                throw new PersistenceException("Cannot save to non-file resource: " + filepath);
            }
        } catch (IOException e) {
            System.out.println("Failed to save data to JSON: " + e.getMessage());
            throw new PersistenceException("Failed to save data to JSON", e);
        }
    }

    /**
     * Retrieves all stocks from the JSONobject.
     *
     * @return a list of all stocks in the JSONobject
     * @throws PersistenceException if there is an issue retrieving the stocks from the JSONobject
     */

    @Override
    public List<Stock> retrieveAll() throws PersistenceException {
        return new ArrayList<>(database.values());
    }

    /**
     * Finds a stock in the JSONobject by its ticker.
     *
     * @param ticker the identifier of the stock
     * @return the stock object with the specified ticker
     * @throws PersistenceException if there is an issue with retrieving the stock from the database
     */

    @Override
    public Stock findById(String ticker) throws PersistenceException {
        return database.get(ticker);
    }

    /**
     * Saves the given stock to the JSON file.
     *
     * @param stock the entity to save
     * @return the saved stock
     * @throws IllegalArgumentException if the stock is null or if any of its properties are invalid
     * @throws PersistenceException if there is an issue with saving the data to the JSON file
     */

    @Override
    public Stock save(Stock stock) throws IllegalArgumentException, PersistenceException {
        if (stock == null) {
            throw new IllegalArgumentException("Stock must not be null");
        }
        if (stock.getTicker() == null || stock.getTicker().trim().isEmpty()) {
            throw new IllegalArgumentException("Stock ticker must not be null or empty");
        }
        if (stock.getName() == null || stock.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Stock name must not be null or empty");
        }
        // Check for non-negative values since these are primitive types and cannot be null.
        if (stock.getCurrentPrice() < 0) {
            throw new IllegalArgumentException("Stock current price must not be negative");
        }
        if (stock.getPurchasePrice() < 0) {
            throw new IllegalArgumentException("Stock purchase price must not be negative");
        }
        if (stock.getQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity must not be negative");
        }

        database.put(stock.getTicker(), stock);
        saveDataToJson(); // Save the updated database to the JSON file
        return stock; // Return the saved stock
    }


    /**
     * Deletes the entity with the specified ticker from the JSON file.
     *
     * @param ticker the unique identifier of the entity to delete
     * @throws IllegalArgumentException if the specified ticker is null or empty
     * @throws PersistenceException if there is an issue with deleting the entity from the JSON file
     */



    @Override
    public void delete(String ticker) throws IllegalArgumentException, PersistenceException {
        if (database.remove(ticker) == null) {
            throw new IllegalArgumentException("Stock not found: " + ticker);
        }
        saveDataToJson();
    }

    /**
     * Updates the stock entity in the JSON file. If the stock with the specified ticker does not exist, an exception is thrown.
     *
     * @param stock the entity to update
     * @return the updated stock entity
     * @throws IllegalArgumentException if the stock is null or if it does not exist in the JSON file
     * @throws PersistenceException if there is an issue with updating the stock in the JSON file
     */

    @Override
    public Stock update(Stock stock) throws IllegalArgumentException, PersistenceException {
        if (!database.containsKey(stock.getTicker())) {
            throw new IllegalArgumentException("Stock not found: " + stock.getTicker());
        }
        database.put(stock.getTicker(), stock);
        saveDataToJson();
        return stock;
    }

    /**
     * Searches for the Stock where the ticker matches the provided string.
     *
     * @param ticker the name of the ticker
     * @return a list of stocks that match the ticker
     */

    @Override
    public List<Stock> searchByTicker(String ticker) {
        if (ticker == null || database == null) {
            return Collections.emptyList(); // or throw an exception, based on your use case
        }
        return database.values().stream()
                .filter(stock -> ticker.equals(stock.getTicker())) // handles null ticker in stock
                .collect(Collectors.toList());
    }

    /**
     * Searches for Stocks where the sector name matches the provided string.
     *
     * @param sector the name of the sector of the stock
     * @return a list of Stocks that match the sector name
     */

    @Override
    public List<Stock> searchBySector(String sector) {
        if (sector == null || database == null) {
            return Collections.emptyList(); // or throw an exception, based on your use case
        }
        return database.values().stream()
                .filter(stock -> sector.equals(stock.getSector())) // handles null sector in stock
                .collect(Collectors.toList());
    }

    /**
     * Sorts stocks based on a specified attribute.
     *
     * @param attribute The attribute to sort by.
     *                  Must be one of the following: "name", "currentprice", "sector", "quantity", "purchaseprice".
     * @return a list of sorted Stocks
     * @throws IllegalArgumentException if the attribute is invalid
     */

    @Override
    public List<Stock> sortByAttribute(String attribute) {
        List<Stock> sortedStocks = new ArrayList<>(database.values());

        switch (attribute.toLowerCase()) {
            case "name":
                sortedStocks.sort(Comparator.comparing(Stock::getName));
                break;
            case "currentprice":
                sortedStocks.sort(Comparator.comparing(Stock::getCurrentPrice));
                break;
            case "sector":
                sortedStocks.sort(Comparator.comparing(Stock::getSector));
                break;
            case "quantity":
                sortedStocks.sort(Comparator.comparing(Stock::getQuantity));
                break;
            case "purchaseprice":
                sortedStocks.sort(Comparator.comparing(Stock::getPurchasePrice));
                break;
            default:
                throw new IllegalArgumentException("Unknown attribute for sorting: " + attribute);
        }

        return sortedStocks;
}



}
