package ru.clevertec.check.service;

import ru.clevertec.check.entities.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> insert(Product product);

    List<Product> findAll();

    void deleteById(String name);

    Product getProductByName(String name);

    void updateStockToTrue(String name);

    void updateStockToFalse(String name);

    void updateCost(String name, double cost);
}
