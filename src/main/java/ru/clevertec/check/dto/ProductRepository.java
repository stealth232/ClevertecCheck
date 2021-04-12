package ru.clevertec.check.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.check.model.product.Product;

import java.util.List;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAll();

    Integer deleteProductByName(String name);

    Integer deleteProductById(Integer id);

    Product getProductById(Integer id);

    Product getProductByName(String name);

    @Modifying
    @Query("update Product p set p.cost = ?1 where p.id = ?2")
    Integer updateCost(Double cost, Integer id);

    @Modifying
    @Query("update Product p set p.stock = ?1 where p.id = ?2")
    Integer updateStock(Boolean stock, Integer id);
}
