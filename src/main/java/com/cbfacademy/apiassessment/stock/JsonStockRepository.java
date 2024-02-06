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
    private final String filepath;
    private final ObjectMapper objectMapper;
    private final Map<String, Stock> database;
    private final ResourceLoader resourceLoader;

    // The filepath is injected from application properties.
    public JsonStockRepository(@Value("${json.file.path}") String filepath, ResourceLoader resourceLoader) {
        this.filepath = filepath;
        System.out.println("Filepath for JSON Respository: " + filepath);
        this.objectMapper = new ObjectMapper();
        this.resourceLoader = resourceLoader;
        this.database = loadDataFromJson();
    }

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

    @Override
    public List<Stock> retrieveAll() throws PersistenceException {
        return new ArrayList<>(database.values());
    }

    @Override
    public Stock findById(String ticker) throws PersistenceException {
        return database.get(ticker);
    }

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



    @Override
    public void delete(String ticker) throws IllegalArgumentException, PersistenceException {
        if (database.remove(ticker) == null) {
            throw new IllegalArgumentException("Stock not found: " + ticker);
        }
        saveDataToJson();
    }

    @Override
    public Stock update(Stock stock) throws IllegalArgumentException, PersistenceException {
        if (!database.containsKey(stock.getTicker())) {
            throw new IllegalArgumentException("Stock not found: " + stock.getTicker());
        }
        database.put(stock.getTicker(), stock);
        saveDataToJson();
        return stock;
    }

    @Override
    public List<Stock> searchByTicker(String ticker) {
        if (ticker == null || database == null) {
            return Collections.emptyList(); // or throw an exception, based on your use case
        }
        return database.values().stream()
                .filter(stock -> ticker.equals(stock.getTicker())) // handles null ticker in stock
                .collect(Collectors.toList());
    }

    @Override
    public List<Stock> searchBySector(String sector) {
        if (sector == null || database == null) {
            return Collections.emptyList(); // or throw an exception, based on your use case
        }
        return database.values().stream()
                .filter(stock -> sector.equals(stock.getSector())) // handles null sector in stock
                .collect(Collectors.toList());
    }

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
