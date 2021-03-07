package ru.clevertec.check.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.clevertec.check.entities.parameters.ProductParameters;
import ru.clevertec.check.entities.user.User;
import ru.clevertec.check.entities.user.UserType;
import ru.clevertec.check.exception.ProductException;
import ru.clevertec.check.utils.builders.Builder;
import ru.clevertec.check.utils.builders.ProductBuilder;
import ru.clevertec.check.utils.mylinkedlist.MyLinkedList;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.clevertec.check.exception.ProductExceptionConstants.SQL_EXCEPTION;

public class Repository {
    static Logger logger = LogManager.getLogger();
    private static DBController controller = new DBController();
    private static Repository instance = new Repository(controller);

    private final static String insertUserQuery = "insert into users (first_name, second_name, age, usertype," +
            "login, password) values (?, ?, ?, ?, ?, ?)";
    private final static String insertProductQuery = "insert into products (name, cost, stock) values (?, ?, ?)";
    private final static String updateQuery = "update products set name = ? where id = ?";
    private final static String updateUserQuery = "update users set usertype = ? where login = ?";
    private final static String updateStockByNameQuery = "update products set stock = ? where name = ?";
    private final static String updateStockByIdQuery = "update products set stock = ? where id = ?";
    private final static String truncateQuery = "truncate table products";
    private final static String dropQuery = "drop table products";
    private final static String dropUsersQuery = "drop table users";
    private final static String deleteQuery = "delete from products where id = ?";
    private final static String deleteUserByLogin = "delete from users where login = ?";
    private final static String selectProductQuery = "select * from products where id = ?";
    private final static String selectUserQuery = "select * from users where id = ?";
    private final static String selectUserByLoginQuery = "select * from users where login = ? and password = ?";
    private final static String findLoginQuery = "select * from users where login = ?";
    private final static String getSizeQuery = "SELECT COUNT (*) AS rowcount FROM products";
    private final static String getUsersSizeQuery = "SELECT COUNT (*) AS rowcount FROM users";
    private final static String createQuery = "create table if not exists products " +
            "(" +
            "id serial constraint product_pk primary key, " +
            "name varchar(255) UNIQUE , " +
            "cost double precision, " +
            "stock boolean);";
    private final static String createUsersQuery = "create table if not exists users " +
            "(" +
            "id serial constraint user_pk primary key," +
            "first_name varchar(255)," +
            "second_name varchar(255)," +
            "age int," +
            "usertype varchar(255)," +
            "login varchar(255) unique," +
            "password varchar(255));";

    public static Repository getInstance() {
        return instance;
    }

    public Repository(DBController controller) {
        this.controller = controller;
        instance = this;
    }

    public static boolean insert(ProductParameters product) throws ProductException {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertProductQuery)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getCost());
            statement.setBoolean(3, product.isStock());
            int rows = statement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            throw new ProductException(SQL_EXCEPTION);
        }
    }

    public static boolean insert(User user) throws ProductException {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertUserQuery)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setInt(3, user.getAge());
            statement.setString(4, String.valueOf(user.getUserType()));
            statement.setString(5, user.getCredentials().entrySet().stream().findFirst().get().getKey());
            statement.setString(6, user.getCredentials().entrySet().stream().findFirst().get().getValue());
            int rows = statement.executeUpdate();
            return rows == 1;
        } catch (SQLException e) {
            throw new ProductException(SQL_EXCEPTION);
        }
    }

    public boolean updateName(ProductParameters product) {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getItemId());
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean updateUserRole(String login, String userType) {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateUserQuery)) {
            statement.setString(1, userType.toUpperCase());
            statement.setString(2, login);
            return statement.executeUpdate() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean updateStockByName(String name, Boolean stock) {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateStockByNameQuery)) {
            statement.setBoolean(1, stock);
            statement.setString(2, name);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateStockById(Integer id, Boolean stock) {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateStockByIdQuery)) {
            statement.setBoolean(1, stock);
            statement.setInt(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean deleteById(Integer id) {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public static boolean deleteByLogin(String login) {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteUserByLogin)) {
            statement.setString(1, login);
            return statement.executeUpdate() == 1;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public ProductParameters getId(Integer id) throws ProductException {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectProductQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Builder productBuilder = new ProductBuilder();
            if (resultSet.next()) {
                productBuilder.setId(resultSet.getInt(1));
                productBuilder.setCost(resultSet.getDouble(3));
                productBuilder.setName(resultSet.getString(2));
                productBuilder.setStock(resultSet.getBoolean(4));
            }
            return productBuilder.getProduct();
        } catch (SQLException e) {
            throw new ProductException(SQL_EXCEPTION);
        }
    }

    public User getUserId(Integer id) throws ProductException {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectUserQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            Map<String, String> credentials = new HashMap<>();
            if (resultSet.next()) {
                credentials.put(resultSet.getString(6), resultSet.getString(7));
                user = new User(resultSet.getInt(1) ,resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), credentials, UserType.valueOf(resultSet.getString(5)));
            }
            return user;
        } catch (SQLException e) {
            throw new ProductException(SQL_EXCEPTION);
        }
    }

    public static User getUserByLogin(String login, String password) throws ProductException {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectUserByLoginQuery)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            User user = null;
            Map<String, String> credentials = new HashMap<>();
            if (resultSet.next()) {
                credentials.put(resultSet.getString(6), resultSet.getString(7));
                user = new User(resultSet.getInt(1) ,resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), credentials, UserType.valueOf(resultSet.getString(5)));
            }
            return user;
        } catch (SQLException e) {
            throw new ProductException(SQL_EXCEPTION);
        }
    }

    public static boolean isLoginExist(String login) throws ProductException {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(findLoginQuery)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new ProductException(SQL_EXCEPTION);
        }
    }

    public Integer getSize() {
        try (Connection connection = controller.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getSizeQuery);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public static Integer getUsersSize() {
        try (Connection connection = controller.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(getUsersSizeQuery);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public boolean removeTable() {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(dropQuery)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public boolean removeUsersTable() {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(dropUsersQuery)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public boolean cleanTable() {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(truncateQuery)) {
            return true;
        } catch (SQLException throwables) {
            return false;
        }
    }

    public boolean createTableProducts() {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(createQuery)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean createTableUsers() {
        try (Connection connection = controller.getConnection();
             PreparedStatement statement = connection.prepareStatement(createUsersQuery)) {
            statement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public boolean fillProductsRepository() {
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

    public static boolean fillUsersRepository() {
        try {
            Map<String, String> credentials1 = new HashMap<>();
            credentials1.put("beckham", "123456");
            instance.insert(new User("David", "Beckham", 43, credentials1, UserType.USER));

            Map<String, String> credentials = new HashMap<>();
            credentials.put("stealth23", "123456");
            instance.insert(new User("Pasha", "Blintsov", 30, credentials, UserType.ADMIN));

            Map<String, String> credentials2 = new HashMap<>();
            credentials2.put("zlatan", "123456");
            instance.insert(new User("Zlatan", "Ibrahimovich", 37, credentials2, UserType.USER));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<ProductParameters> getProductList() {
        List<ProductParameters> list = new MyLinkedList<>();
        instance.createTableProducts();
        instance.fillProductsRepository();
        for (int i = 1; i < instance.getSize() + 1; i++) {
            try {
                list.add(instance.getId(i));
            } catch (ProductException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<ProductParameters> getCurrentProductList() {
        List<ProductParameters> list = new ArrayList<>();
        for (int i = 0; i < instance.getSize() + 10; i++) {
            try {
                if(instance.getId(i).getName()!=null){
                    list.add(instance.getId(i));
                }
            } catch (ProductException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<User> getBaseUserList() {
        List<User> list = new MyLinkedList<>();
        instance.removeUsersTable();
        instance.createTableUsers();
        instance.fillUsersRepository();
        for (int i = 0; i < instance.getUsersSize() ; i++) {
            try {
                list.add(instance.getUserId(i));
            } catch (ProductException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static List<User> getCurrentUserList() {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < instance.getUsersSize()+10 ; i++) {
            try {
                if(instance.getUserId(i)!=null)
                list.add(instance.getUserId(i));
            } catch (ProductException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
