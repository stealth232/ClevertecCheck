package ru.clevertec.check.dao;

import ru.clevertec.check.utils.builders.Builder;
import ru.clevertec.check.utils.builders.ProductBuilder;
import ru.clevertec.check.parameters.ProductParameters;

import java.sql.*;

public class Repository {
    private static Repository instance = null;
    private static DBController controller;

    private String insertQuery = "insert into products (name, cost, stock) values (?, ?, ?)";
    private String updateQuery = "update products set name = ? where id = ?";
    private String updateStockByNameQuery = "update products set stock = ? where name = ?";
    private String updateStockByIdQuery = "update products set stock = ? where id = ?";
    private String truncateQuery = "truncate table products";
    private String dropQuery = "drop table products";
    private String deleteQuery = "delete from products where id = ?";
    private String selectQuery = "select * from products where id = ?";
    private String getSizeQuery = "SELECT COUNT (*) AS rowcount FROM products";
    private String createQuery = "create table products " +
            "(" +
            "id serial constraint product_pk primary key, " +
            "name varchar(255) UNIQUE , " +
            "cost double precision, " +
            "stock boolean);";

    public static Repository getInstance(DBController controller) {
        if (instance == null)
            instance = new Repository(controller);

        return instance;
    }

    public Repository(DBController controller) {
        this.controller = controller;
    }

    public boolean insert(ProductParameters product) {
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getCost());
            statement.setBoolean(3, product.isStock());
            int rows = statement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateName(ProductParameters product) {
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, product.getName());
            statement.setInt(2, product.getItemId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateStockByName(String name, Boolean stock) {
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateStockByNameQuery);
            statement.setBoolean(1, stock);
            statement.setString(2, name);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateStockById(Integer id, Boolean stock) {
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateStockByIdQuery);
            statement.setBoolean(1, stock);
            statement.setInt(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteById(Integer id) {
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public ProductParameters getId(Integer id) {
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Builder productBuilder = new ProductBuilder();
            while (resultSet.next()) {
                productBuilder.setId(resultSet.getInt(1));
                productBuilder.setCost(resultSet.getDouble(3));
                productBuilder.setName(resultSet.getString(2));
                productBuilder.setStock(resultSet.getBoolean(4));

            }
            return productBuilder.getProduct();
        } catch (SQLException e) {
            return null;
        }
    }

    public Integer getSize() {
        try (Connection connection = controller.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getSizeQuery);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean removeTable() {
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(dropQuery);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public boolean cleanTable() {
        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(truncateQuery);
            return true;
        } catch (SQLException throwables) {

            return false;
        }
    }

    public boolean createTable() {

        try (Connection connection = controller.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(createQuery);
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public boolean fillRepository() {
        try {
            Builder snickers = new ProductBuilder();
            snickers.setName("Snickers");
            snickers.setCost(1.3);
            snickers.setStock(false);
            instance.insert(snickers.getProduct());

            Builder twix = new ProductBuilder();
            twix.setName("Twix");
            twix.setCost(1.56);
            twix.setStock(true);
            instance.insert(twix.getProduct());

            Builder mars = new ProductBuilder();
            mars.setName("Mars");
            mars.setCost(1.67);
            mars.setStock(false);
            instance.insert(mars.getProduct());

            Builder kitkat = new ProductBuilder();
            kitkat.setName("KitKat");
            kitkat.setCost(1.2);
            kitkat.setStock(true);
            instance.insert(kitkat.getProduct());

            Builder alonka = new ProductBuilder();
            alonka.setName("Alonka");
            alonka.setCost(2.3);
            alonka.setStock(false);
            instance.insert(alonka.getProduct());

            Builder nuts = new ProductBuilder();
            nuts.setName("Nuts");
            nuts.setCost(2.1);
            nuts.setStock(true);
            instance.insert(nuts.getProduct());

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
