package ru.clevertec.check.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clevertec.check.dao.UserDao;
import ru.clevertec.check.entities.user.User;
import ru.clevertec.check.service.UserService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void updateUserRole(int id) {
        userDao.updateUserRole(id);
    }

    @Override
    public User getUser(String login, String password) {
        return userDao.getUser(login, password);
    }

    @Override
    public User isUserExist(String login){
        return userDao.isUserExist(login);
    }

    @Override
    public Optional<User> insert(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDao.insert(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void deleteByLogin(String login) {
        userDao.deleteByLogin(login);
    }
}
