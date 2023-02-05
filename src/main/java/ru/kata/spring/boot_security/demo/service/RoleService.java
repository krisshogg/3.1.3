package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public interface RoleService extends UserDetailsService {
    List<User> findAllUsers();

    User findUser(Long userId) throws NullPointerException;

    void deleteUser(Long userId);

    List<Role> findAllRoles();

    void tryIndex(Model model, HttpSession session, LoginException authenticationException, String authenticationName);

    boolean saveUser(User user, BindingResult bindingResult, Model model);
}
