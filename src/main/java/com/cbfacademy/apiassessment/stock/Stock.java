package com.cbfacademy.apiassessment.stock;


/**
 * The Stock class represents a stock in the system.
 */
public class Stock {
    /**
     * The ticker variable represents the ticker symbol of a stock. It is a private field in the Stock class
     * and is immutable since it serves as a unique identifier for the stock
     *.
     */
    private String ticker;

    /**
     * The name variable represents the name of a stock. It is a private field in the Stock class
     * and is mutable, meaning it can be changed using the setName() method.
     */
    private String name; // Name of stock

    /**
     * The currency symbol associated with the stock.
     *
     * <p>This variable represents the currency symbol used for the stock. It is a string type, containing
     * the symbol for the currency used by the stock. The currency symbol typically consists of one or more
     * characters and is used to differentiate between different types of currencies.
     *
     * <p>The currency symbol is a private variable, accessible only within the {@link Stock} class and its
     * subclasses. It can be set using the {@link Stock#setCurrencySymbol(String)} method and retrieved using
     * the {@link Stock#getCurrencySymbol()} method.
     *
     * <p>Note: The currency symbol does not include any currency-related formatting such as currency codes
     * or punctuation. It is solely the symbol used to represent the currency.
     */
    private String currencySymbol;

    /**
     * Represents the sector of a stock.
     *
     * The sector represents the industry or category that a stock belongs to.
     */
    private String sector;

    /**
     * Represents the current market price of a stock.
     * This variable is private, accessible only within the containing class.
     */
    private double currentPrice;

    /**
     * Represents the number of shares owned.
     *
     * The quantity variable stores the number of shares owned for a specific stock.
     * It is a private integer field within the Stock class.
     *
     * Usage:
     * To access or modify the quantity, use the corresponding getter and setter methods in the Stock class.
     *
     * @see Stock
     */
    private int quantity;

    /**
     * Represents the average purchase price of a stock.
     */
    private double purchasePrice;

    // No-argument constructor for Jackson

    public Stock() {

    }

    // Constructor for generating a stock

    /**
     * Represents a stock object.
     */
    public Stock(String ticker, String name, String currencySymbol, String sector, Double currentPrice, Integer quantity, Double purchasePrice) {
        if (ticker == null || name == null || currencySymbol == null || sector == null) {
            throw new IllegalArgumentException("Ticker, Name, Currency Symbol, and Sector cannot be null");
        }
        if (currentPrice == null || currentPrice < 0) {
            throw new IllegalArgumentException("Current Price cannot be null or negative");
        }
        if (quantity == null || quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be null or negative");
        }
        if (purchasePrice == null || purchasePrice < 0) {
            throw new IllegalArgumentException("Purchase Price cannot be null or negative");
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
