package ru.clevertec.check.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.check.dto.UserRepository;
import ru.clevertec.check.model.user.Role;
import ru.clevertec.check.model.user.User;
import ru.clevertec.check.service.UserService;

import java.util.*;

import static ru.clevertec.check.service.CheckConstants.ROLE_ADMIN;
import static ru.clevertec.check.service.CheckConstants.ROLE_USER;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserByLogin(String login) {
        return userRepository.getUserByLogin(login);
    }

    @Override
    public User save(User user) {
        User userFromDB = userRepository.getUserByLogin(user.getLogin());
        if (Objects.isNull(userFromDB)) {
            user.setRoles(Collections.singleton(new Role(2L, ROLE_USER)));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Integer deleteUserById(Integer id) {
        return userRepository.deleteUserById(id);
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public Integer changeRole(Integer id) {
        User user = userRepository.getUserById(id);
        Set<Role> role = new HashSet<>();
        if (user.getRoles().stream().findFirst().get().getName().equals(ROLE_USER)) {
            role.add(new Role(1L, ROLE_ADMIN));
        }
        if (user.getRoles().stream().findFirst().get().getName().equals(ROLE_ADMIN)) {
            role.add(new Role(2L, ROLE_USER));
        }
        user.setRoles(role);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Optional<User> getUser(String login, String password) {
        User user = userRepository.getUserByLogin(login);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return userRepository.findUserByLogin(login);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                grantedAuthorities);
    }
}
