package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {
    List<User> findAll();

    User find(int id);

    boolean save(User user);

    void delete(User user);
}
