package ru.clevertec.check.service;

import ru.clevertec.check.entities.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUser(String login, String password);

    User isUserExist(String login);

    void updateUserRole(int id);

    Optional<User> insert(User user);

    List<User> findAll();

    void deleteByLogin(String login);


}
