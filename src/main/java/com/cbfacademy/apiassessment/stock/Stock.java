package com.cbfacademy.apiassessment.stock;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Stock {
    @Id
    private String ticker;
    private String name;
    private String currencySymbol;

    private String sector;
    private double currentPrice;  // Current market price
    private int quantity;  // Number of shares owned
    private double purchasePrice;  // Average purchase price

    // No-argument constructor
    public Stock() {
    }

    //constructor for generating a stock
    public Stock(String ticker, String name, String currencySymbol, String sector, double currentPrice, int quantity, double purchasePrice) {
        if (ticker == null) {
            throw new IllegalArgumentException("Ticker cannot be null");
        }
        this.ticker = ticker;
        this.name = name;
        this.currencySymbol = currencySymbol;
        this.sector = sector;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    //
    public Stock(String ticker, String name, String currencySymbol, String sector) {
        if (ticker == null) {
            throw new IllegalArgumentException("Ticker cannot be null");
        }
        this.ticker = ticker;
        this.name = name;
        this.currencySymbol = currencySymbol;
        this.sector= sector;
    }

    //
    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public String getSector() {
        return sector;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }


    // Setters for mutable fields
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }


}
