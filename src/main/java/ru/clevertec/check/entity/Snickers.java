package ru.clevertec.check.entity;

import ru.clevertec.check.entity.impl.Product;

public class Snickers extends Product {
    private int itemId = 1;
    private String name = "Snickers";
    private double cost = 1.99;
    private boolean stock = true;

    public Snickers() {
    }

    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean x) {
        stock = x;
    }

    @Override
    public String toString() {
        return name;
    }
}
