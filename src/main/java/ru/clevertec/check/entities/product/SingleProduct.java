package ru.clevertec.check.entities.product;

import ru.clevertec.check.entities.parameters.ProductParameters;

public class SingleProduct {
    private int quantity;
    private String name;
    private double price;
    private double totalPrice;
    private ProductParameters product;

    public SingleProduct(int quantity, String name,
                         double price, double totalPrice, ProductParameters product) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.totalPrice = totalPrice;
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ProductParameters getProduct() {
        return product;
    }

    public void setProduct(ProductParameters product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "\n SingleProduct{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", product=" + product +
                '}'
                ;
    }
}
