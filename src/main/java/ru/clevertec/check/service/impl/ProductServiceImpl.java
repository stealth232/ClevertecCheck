package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.check.dao.ProductDao;
import ru.clevertec.check.entities.product.Product;
import ru.clevertec.check.service.ProductService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    @Override
    public Optional<Product> insert(Product product) {
        return productDao.insert(product);
    }

    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void deleteById(String name) {
        productDao.deleteById(name);
    }

    @Override
    public Product getProductByName(String name) {
        return productDao.getProductByName(name);
    }

    @Override
    public void updateStockToTrue(String name) {
        productDao.updateStockToTrue(name);
    }

    @Override
    public void updateStockToFalse(String name) {
        productDao.updateStockToFalse(name);
    }

    @Override
    public void updateCost(String name, double cost) {
        productDao.updateCost(name, cost);
    }
}
