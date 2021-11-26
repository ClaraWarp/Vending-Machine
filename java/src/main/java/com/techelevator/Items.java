package com.techelevator;

import java.math.BigDecimal;

public abstract class Items {

    private String name;
    private BigDecimal price;
    private int stock = 5;

    public Items(String name, String Price) {
        this.name = name;
        this.price = BigDecimal.valueOf(Double.parseDouble(Price));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void adjustStock() {
        stock--;
    }

    abstract String getDispensingMessage();

}
