package com.cbfacademy.apiassessment;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Stock {
    private String ticker;
    private String name;
    private String currencySymbol;
    private double price;  // Current market price
    private int quantity;  // Number of shares owned
    private double purchasePrice;  // Average purchase price

    // No-argument constructor
    public Stock() {
    }

    //constructor for generating a stock
    public Stock(String ticker, String name, String currencySymbol, double price, int quantity, double purchasePrice) {
        this.ticker = ticker;
        this.name = name;
        this.currencySymbol = currencySymbol;
        this.price = price;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    // 
    public Stock(String ticker, String name, String currencySymbol) {
        this.ticker = ticker;
        this.name = name;
        this.currencySymbol = currencySymbol;
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
