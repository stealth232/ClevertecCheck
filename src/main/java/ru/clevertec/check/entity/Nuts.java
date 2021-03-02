package ru.clevertec.check.entity;

import ru.clevertec.check.entity.impl.Product;

public class Nuts extends Product {

    private int itemId = 4;
    private String name = "Nuts";
    private double cost = 2.29;
    private boolean stock = false;

    public Nuts() {
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