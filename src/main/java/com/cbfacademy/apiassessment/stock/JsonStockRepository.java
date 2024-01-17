package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.core.PersistenceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JsonStockRepository implements StockRepository {
    private final String filepath;
    private final ObjectMapper objectMapper;
    private final Map<Stock, String> database;

    public JsonStockRepository(@Value("${json.file.path}") String filepath) {
        this.filepath = filepath;
        this.objectMapper = new ObjectMapper();
        this.database = loadDataFromJson();
    }

    private Map<Stock, String> loadDataFromJson() {
        File file = new File(filepath);
        try {
            if (file.exists()) {
                return objectMapper.readValue(file, new TypeReference<Map<Stock, String>>() {
                });
            }

        } catch (IOException e) {
            throw new PersistenceException("Failed to load data from JSON", e);

        }
        return new HashMap<>();
    }

    // Save the state to JSON
    private void saveDataToJson() {
        try {
            objectMapper.writeValue(new File(filepath), database);
        } catch (IOException e) {
            throw new PersistenceException("Failed to save data to JSON", e);
        }

    }

    @Override
    public List<Stock> retrieveAll() throws PersistenceException {
        return null;
    }

    @Override
    public Stock findById(String s) {
        return null;
    }

    @Override
    public Stock save(Stock entity) throws IllegalArgumentException, PersistenceException {
        return null;
    }

    @Override
    public void delete(String s) throws IllegalArgumentException, PersistenceException {

    }

    @Override
    public Stock update(Stock entity) throws IllegalArgumentException, PersistenceException {
        return null;
    }

    @Override
    public List<Stock> searchByTicker(String ticker) {
        return null;
    }

    @Override
    public List<Stock> searchByIndustry(String industry) {
        return null;
    }
}
