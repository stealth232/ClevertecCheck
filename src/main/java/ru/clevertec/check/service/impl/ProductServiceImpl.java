package ru.clevertec.check.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.check.dto.ProductRepository;
import ru.clevertec.check.model.product.Product;
import ru.clevertec.check.service.ProductService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Integer deleteProductByName(String name) {
        return productRepository.deleteProductByName(name);
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.getProductById(id);
    }

    @Override
    public Integer deleteProductById(Integer id) {
        return productRepository.deleteProductById(id);
    }

    @Override
    public Integer changeStockById(Integer id) {
        Product product = productRepository.getProductById(id);
        Boolean stock = null;
        if (product.isStock()) {
            stock = Boolean.FALSE;
        } else if (!product.isStock()) {
            stock = Boolean.TRUE;
        }
        return productRepository.updateStock(stock, id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Integer updateCost(Double cost, Integer id){
      return   productRepository.updateCost(cost, id);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.getProductByName(name);
    }
}
