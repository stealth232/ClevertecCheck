package ru.clevertec.check.model.product;


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
}
