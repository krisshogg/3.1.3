package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

//import javax.persistence.PersistenceException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {


    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public RoleServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.find(userId)
                .orElseThrow(() -> new EmptyResultDataAccessException(String.format("User with ID = %d not found", userId), 1));
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.find(email);
        if (null == user) {
            throw new UsernameNotFoundException(String.format("User email %s not found", email));
        }
        return user;
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> user = userRepository.find(userId);
        if (user.isPresent()) {
            try {
                userRepository.delete(user.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void tryIndex(Model model, HttpSession session, LoginException authenticationException, String authenticationName) {
        if (authenticationException != null) { // Восстанавливаем неверно введенные данные
            try {
                model.addAttribute("authenticationException", authenticationException);
                session.removeAttribute("Authentication-Exception");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            model.addAttribute("authenticationException", new LoginException(null));
        }

        if (authenticationName != null) { // Выводим прощальное сообщение
            try {
                model.addAttribute("authenticationName", authenticationName);
                session.removeAttribute("Authentication-Name");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean saveUser(User user, BindingResult bindingResult, Model model) {
        model.addAttribute("allRoles", findAllRoles());

        if (bindingResult.hasErrors()) {
            return false;
        }

        for (Role role : user.getRoles()) {
            try {
                role.setId(roleRepository.findRoleByAuthority(role.getAuthority()).getId());
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            userRepository.save(user);
        } catch (Exception e) { // org.hibernate.exception.ConstraintViolationException
            model.addAttribute("persistenceException", true);
            return false;
        }

        return true;
    }
}
