package ru.clevertec.check.entities.product;

import ru.clevertec.check.entities.parameters.ProductParameters;

public class Product implements ProductParameters {
    private int itemId;
    private String name;
    private double cost;
    private boolean stock;

    public Product(int itemId, String name, double cost, boolean stock) {
        this.itemId = itemId;
        this.name = name;
        this.cost = cost;
        this.stock = stock;
    }
    public Product(String name, double cost, boolean stock) {
        this.name = name;
        this.cost = cost;
        this.stock = stock;
    }
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int getItemId() {
        return this.itemId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getCost() {
        return this.cost;
    }

    @Override
    public boolean isStock() {
        return this.stock;
    }

    @Override
    public void setStock(boolean stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", stock=" + stock +
                '}';
    }

}
