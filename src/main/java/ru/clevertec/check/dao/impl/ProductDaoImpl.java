package ru.clevertec.check.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.annotations.log.LogLevel;
import ru.clevertec.check.annotations.log.LogMe;
import ru.clevertec.check.dao.ProductDao;
import ru.clevertec.check.entities.product.Product;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ProductDaoImpl implements ProductDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String insertProductQuery = "insert into products (name, cost, stock) values (?, ?, ?) " +
            "returning id, name , cost, stock ";
    private static final String updateCost = "update products set cost = ? where name = ?";
    private static final String updateStockByName = "update products set stock = ? where products.name = ?";
    private static final String getAllQuery = "SELECT name, cost, stock FROM products";
    private static final String deleteQuery = "delete from products where products.name = ?";
    private static final String selectProductByName = "select from products where products.name = ?";

    private static final String createQuery = "create table if not exists products " +
            "(" +
            "id serial constraint product_pk primary key, " +
            "name varchar(255) UNIQUE , " +
            "cost double precision, " +
            "stock boolean);";

    @LogMe(LogLevel.ERROR)
    @Override
    public Optional<Product> insert(Product product) {
        return jdbcTemplate.query(insertProductQuery,
                new Object[]{product.getName(), product.getCost(), product.isStock()},
                new BeanPropertyRowMapper<>(Product.class)).stream().findAny();
    }

    @LogMe(LogLevel.ERROR)
    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(getAllQuery, new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public void updateCost(String name, double cost) {
        jdbcTemplate.update(updateCost, new Object[]{cost, name});
    }

    @Override
    public void updateStockToTrue(String name) {
        jdbcTemplate.update(updateStockByName, new Object[]{true, name});
    }

    @Override
    public void updateStockToFalse(String name) {
        jdbcTemplate.update(updateStockByName, new Object[]{false, name});
    }

    @Override
    public Product getProductByName(String name) {
        return jdbcTemplate.query(selectProductByName, new Object[]{name}, new BeanPropertyRowMapper<>(Product.class)).stream()
                .findAny().orElse(null);
    }

    @Override
    public void deleteById(String name) {
        jdbcTemplate.update(deleteQuery, name);
    }
}
