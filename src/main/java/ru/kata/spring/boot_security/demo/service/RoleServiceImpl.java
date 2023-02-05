package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

//import javax.persistence.PersistenceException;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
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
    public User findUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EmptyResultDataAccessException(String.format("User with ID = %d not found", userId), 1));
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException(String.format("User email %s not found", username));
        }
        return user.get();
    }

    @Override
    public void deleteUser(long userId) {
        roleRepository.deleteById(userId);
    }

    @Override
    public void updateUser(long id, Role updateRole) {
        updateRole.setId(id);
        roleRepository.save(updateRole);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

/*    @Override
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
    }*/

    @Override
    public void saveUser(Role role) {
        roleRepository.save(role);
    }
}
