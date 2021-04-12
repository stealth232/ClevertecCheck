package ru.clevertec.check.service;

import ru.clevertec.check.model.product.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();

    Integer deleteProductByName(String name);

    Product getProductById(Integer id);

    Integer deleteProductById(Integer id);

    Integer changeStockById(Integer id);

    Product save(Product product);

    Integer updateCost(Double cost, Integer id);

    Product getProductByName(String name);
}
