package com.crypto.model;

public class Crypto {
    private String symbol;
    private String name;
    private double currentPrice;

    public Crypto(String symbol, String name, double currentPrice) {
        this.symbol = symbol;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public double getCurrentPrice() { return currentPrice; }

    @Override
    public String toString() {
        return String.format("%-10s %-20s $%.2f", symbol, name, currentPrice);
    }
}
