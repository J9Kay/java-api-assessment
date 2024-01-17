package com.cbfacademy.apiassessment.stock;

import com.cbfacademy.apiassessment.core.PersistenceException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JSONStockRepository implements StockRepository {
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
