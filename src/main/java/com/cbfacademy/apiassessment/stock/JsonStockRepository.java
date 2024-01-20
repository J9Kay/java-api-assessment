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

@Repository
public class JsonStockRepository implements StockRepository {
    private final String filepath;
    private final ObjectMapper objectMapper;
    private final Map<String, Stock> database;
    private final ResourceLoader resourceLoader;

    // The filepath is injected from application properties.
    public JsonStockRepository(@Value("${json.file.path}") String filepath, ResourceLoader resourceLoader) {
        this.filepath = filepath;
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
        // Saving to JSON should be handled with care, especially if it's a classpath resource.
        try {
            Resource resource = resourceLoader.getResource(filepath);
            if (resource.isFile()) {
                objectMapper.writeValue(resource.getFile(), database);
            } else {
                throw new PersistenceException("Cannot save to non-file resource: " + filepath);
            }
        } catch (IOException e) {
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
    public Stock save(Stock entity) throws IllegalArgumentException, PersistenceException {
        return null;
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


}
