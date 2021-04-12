package ru.clevertec.check.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.check.model.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserByLogin(String login);

    Optional<User> findUserByLogin(String login);

    Integer deleteUserById(Integer login);

    User getUserById(Integer id);

    Optional<User> getUserByLoginAndPassword(String login, String password);

}
