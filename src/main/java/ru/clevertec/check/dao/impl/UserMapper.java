package ru.clevertec.check.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import ru.clevertec.check.entities.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(1));
        user.setFirstName(resultSet.getString(2));
        user.setSecondName(resultSet.getString(3));
        user.setAge(resultSet.getInt(4));
        user.setUserType(resultSet.getString(5));
        user.setLogin(resultSet.getString(6));
        user.setPassword(resultSet.getString(7));
        return user;
    }
}
