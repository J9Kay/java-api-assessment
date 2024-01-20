package com.cbfacademy.apiassessment.stock;


public class Stock {
    private String ticker; // Immutable field as it's a unique identifier
    private String name; // Name of stock
    private String currencySymbol;
    private String sector;
    private double currentPrice;  // Current market price
    private int quantity;  // Number of shares owned
    private double purchasePrice;  // Average purchase price

    // No-argument constructor for Jackson
    public Stock() {

    }

    // Constructor for generating a stock
    public Stock(String ticker, String name, String currencySymbol, String sector, double currentPrice, int quantity, double purchasePrice) {
        if (ticker == null || name == null || currencySymbol == null || sector == null) {
            throw new IllegalArgumentException("Ticker, Name, Currency Symbol, and Sector cannot be null");
        }
        this.ticker = ticker;
        this.name = name;
        this.currencySymbol = currencySymbol;
        this.sector = sector;
        this.currentPrice = currentPrice;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    // Getters
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
    public void setName(String name) {
        this.name = name;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

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
