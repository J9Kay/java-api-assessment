package com.cbfacademy.apiassessment;

public class Stock {
    private final String ticker;
    private final String name;
    private final String currencySymbol;
    private double price;  // Current market price
    private int quantity;  // Number of shares owned
    private double purchasePrice;  // Average purchase price

    //constructor for generating a stock


    //constructor for generating a stock
    public Stock(String ticker, String name, String currencySymbol, double price, int quantity, double purchasePrice) {
        this.ticker = ticker;
        this.name = name;
        this.currencySymbol = currencySymbol;
        this.price = price;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    // Constructor for immutable fields
    public Stock(String ticker, String name, String currencySymbol) {
        this.ticker = ticker;
        this.name = name;
        this.currencySymbol = currencySymbol;
    }

    // Getters for all fields
    public String getTicker() {
        return ticker;
    }

    public String getName() {
        return name;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    // Setters for mutable fields
    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }


}
