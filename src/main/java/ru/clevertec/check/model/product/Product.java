package ru.clevertec.check.model.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
