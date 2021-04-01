package ru.clevertec.check.entities.product;

import lombok.*;
import ru.clevertec.check.entities.parameters.ProductParameters;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product implements ProductParameters {
    private int id;

    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 20, message = "Product name must contains 3 - 20 symbols")
    private String name;

    @NotNull
    @DecimalMin(value = "0.01", message = "Min price is 0.01")
    @DecimalMax(value = "1000.0", message = "Max price is 1000")
    private double cost;

    @NotNull
    private boolean stock;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public int getItemId() {
        return this.id;
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
                "itemId=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", stock=" + stock +
                '}';
    }
}
