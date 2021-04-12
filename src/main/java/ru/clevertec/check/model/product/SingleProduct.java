package ru.clevertec.check.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SingleProduct {
    private int quantity;
    private String name;
    private double price;
    private double totalPrice;
    private Product product;
}
