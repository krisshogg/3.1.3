package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService extends UserDetailsService {
    List<User> findAll();

    Optional<User> findById(int id);

    void save(User user);

    void delete(User user);

    void update(int id, User updateUser);

    void addRole(Role role);

    void addUser(User user);
}
