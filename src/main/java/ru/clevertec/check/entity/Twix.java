package ru.clevertec.check.entity;

import ru.clevertec.check.entity.impl.Product;

public class Twix extends Product {
    private int itemId = 5;
    private String name = "Twix";
    private double cost = 1.79;
    private boolean stock = true;

    public Twix() {
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