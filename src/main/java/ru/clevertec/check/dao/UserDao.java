package ru.clevertec.check.dao;

import ru.clevertec.check.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    void updateUserRole(int id);

    Optional<User> insert(User user);

    void deleteByLogin(String login);

    User getUserById(int id);

    List<User> findAll();

    User isUserExist(String login);

    User getUser(String login, String password);
}
