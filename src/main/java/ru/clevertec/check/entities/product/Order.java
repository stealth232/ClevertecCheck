package ru.clevertec.check.entities.product;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    private List<SingleProduct> products;
    private Card card;
    private double cardPercent;
    private double discount;
    private double totalPrice;
    private String date = new Date().toString();

    public Order( List<SingleProduct> products, Card card,
                 double cardPercent, double discount, double totalPrice) {
        this.products = products;
        this.card = card;
        this.cardPercent = cardPercent;
        this.discount = discount;
        this.totalPrice = totalPrice;
    }

    public List<SingleProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SingleProduct> products) {
        this.products = products;
    }

    public int getCard() {
        return card.getNumber();
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public double getCardPercent() {
        return (1-cardPercent)*100 ;
    }

    public void setCardPercent(double cardPercent) {
        this.cardPercent = cardPercent;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Order{" +
                "products=" + products +
                ", card=" + card.getNumber() +
                ", cardPercent=" + cardPercent +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", date='" + date + '\'' +
                '}';
    }
}
