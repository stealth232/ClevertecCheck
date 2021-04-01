package ru.clevertec.check.dao;

import ru.clevertec.check.entities.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    Optional<Product> insert(Product product);

    List<Product> findAll();

    void updateCost(String name, double cost);

    void updateStockToFalse(String name);

    void updateStockToTrue(String name);

    Product getProductByName(String name);

    void deleteById(String name);

}
