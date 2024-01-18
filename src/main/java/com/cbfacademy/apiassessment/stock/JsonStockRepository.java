package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JsonStockRepository implements StockRepository {
    private final String filepath;
    private final ObjectMapper objectMapper;
    private final Map<String, Stock> database;

    public JsonStockRepository(@Value("${json.file.path}") String filepath) {
        this.filepath = filepath;
        this.objectMapper = new ObjectMapper();
        this.database = loadDataFromJson();
    }

    private Map<String, Stock> loadDataFromJson() {
        File file = new File(filepath);
        try {
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<Map<String, Stock>>() {});

            }
        } catch (IOException e) {
            e.printStackTrace(); // Or use a logger if available
            throw new PersistenceException("Failed to load data from JSON", e);
        }
        return new HashMap<>();
    }

    private void saveDataToJson() {
        try {
            objectMapper.writeValue(new File(filepath), database);
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
    public Stock save(Stock stock) throws IllegalArgumentException, PersistenceException {
        database.put(stock.getTicker(), stock);
        saveDataToJson();
        return stock;
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
        return database.values().stream()
                .filter(stock -> stock.getTicker().equals(ticker))
                .collect(Collectors.toList());
    }

    @Override
    public List<Stock> searchBySector(String sector) {
        return database.values().stream()
                .filter(stock -> stock.getSector().equals(sector))
                .collect(Collectors.toList());
    }
}
