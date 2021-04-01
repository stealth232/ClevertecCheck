package ru.clevertec.check.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.check.dao.UserDao;
import ru.clevertec.check.entities.user.User;
import ru.clevertec.check.entities.user.UserType;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    private static final String insertUserQuery = "insert into users (first_name, second_name, age, usertype," +
            "login, password) values (?, ?, ?, ?, ?, ?) " +
            "returning id, first_name , second_name, age, usertype, login, password";
    private static final String updateUserQuery = "update users set usertype = ? where id = ?";
    private static final String deleteUserByLogin = "delete from users where login = ?";
    private static final String selectUserById = "select * from users where id = ?";
    private static final String selectUserByLogin = "select * from users where login = ?";
    private static final String authUser = "select * from users where login = ? and password = ?";
    private static final String getAllQuery = "SELECT id, first_name, second_name, age, usertype, login, password FROM users";
    private static final String createUsersQuery = "create table if not exists users " +
            "(" +
            "id serial constraint user_pk primary key," +
            "first_name varchar(255)," +
            "second_name varchar(255)," +
            "age int," +
            "usertype varchar(255)," +
            "login varchar(255) unique," +
            "password varchar(255));";

    @Override
    public Optional<User> insert(User user) {
        return jdbcTemplate.query(insertUserQuery,
                new Object[]{user.getFirstName(),
                        user.getSecondName(), user.getAge(),
                        UserType.USER.toString(), user.getLogin(), user.getPassword()},
                new UserMapper()).stream().findAny();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(getAllQuery, new UserMapper());
    }

    @Override
    public void updateUserRole(int id) {
        User user = getUserById(id);
        String role = null;
        if (user.getUserType().equals(UserType.USER)) {
            role = "ADMIN";
        } else if (user.getUserType().equals(UserType.ADMIN)) {
            role = "USER";
        }
        jdbcTemplate.update(updateUserQuery, new Object[]{role, id});
    }

    @Override
    public void deleteByLogin(String login) {
        jdbcTemplate.update(deleteUserByLogin, login);
    }

    @Override
    public User getUserById(int id) {
        return jdbcTemplate.query(selectUserById, new Object[]{id}, new UserMapper()).stream()
                .findAny().orElse(null);
    }

    @Override
    public User isUserExist(String login) {
        return jdbcTemplate.query(selectUserByLogin, new Object[]{login}, new UserMapper()).stream()
                .findAny().orElse(null);
    }

    @Override
    public User getUser(String login, String password) {
        return jdbcTemplate.query(authUser, new Object[]{login, password}, new UserMapper()).stream().findAny().orElse(null);
    }
}
