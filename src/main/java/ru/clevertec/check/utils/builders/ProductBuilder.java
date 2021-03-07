package ru.clevertec.check.utils.builders;

import ru.clevertec.check.entities.product.Product;

public class ProductBuilder implements Builder {
    private int itemId;
    private String name;
    private double cost;
    private boolean stock;

    @Override
    public void setId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public void setStock(boolean stock) {
        this.stock = stock;
    }

    @Override
    public Product getProduct() {
        return new Product(itemId, name, cost, stock);
    }

}
