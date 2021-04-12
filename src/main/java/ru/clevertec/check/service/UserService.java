package ru.clevertec.check.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.clevertec.check.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    User getUserByLogin(String login);

    User save(User user);

    List<User> findAll();

    Integer deleteUserById(Integer id);

    Optional<User> findUserByLogin(String login);

    Integer changeRole(Integer id);

    Optional<User> getUser(String login, String password);

    UserDetails loadUserByUsername(String login) throws UsernameNotFoundException;
}
