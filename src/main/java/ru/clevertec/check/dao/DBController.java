package ru.clevertec.check.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class DBController {
    private final String HOST = "jdbc:postgresql://localhost:5432/postgres";
    private final String username = "postgres";
    private final String password = "123456";

    public Connection getConnection() throws SQLException {
        checkDriverRegistered();
        Connection connection = DriverManager.getConnection(HOST,username,password);

        if(Objects.nonNull(connection))
            return connection;
        else throw new SQLException();
    }

    private void checkDriverRegistered() {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver is not present");
            e.printStackTrace();
            return;
        }
    }
}
