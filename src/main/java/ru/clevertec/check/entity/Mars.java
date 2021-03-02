package ru.clevertec.check.entity;

import ru.clevertec.check.entity.impl.Product;

public class Mars extends Product {
    private int itemId = 2;
    private String name = "Mars";
    private double cost = 1.59;
    private boolean stock = true;

    public Mars() {
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