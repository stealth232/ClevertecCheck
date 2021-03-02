package ru.clevertec.check.entity;

import ru.clevertec.check.entity.impl.Product;

import java.util.Objects;

public class Bounty extends Product {
    private int itemId = 3;
    private String name = "Bounty";
    private double cost = 1.29;
    private boolean stock = false;

    public Bounty() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bounty bounty = (Bounty) o;
        return itemId == bounty.itemId &&
                Double.compare(bounty.cost, cost) == 0 &&
                stock == bounty.stock &&
                Objects.equals(name, bounty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, name, cost, stock);
    }

    @Override
    public String toString() {
        return name;
    }
}