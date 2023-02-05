
package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component

public class DataLoader implements ApplicationRunner{
    private final UserService userService;

    @Autowired
    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    public void run(ApplicationArguments args) {

        User cat = new User( "Матроскин", "cat", "qqq@cat.ru", Collections.singleton(new Role ("ROLE_USER")));
        User dog = new User( "Барбоскин", "dog", "www@dog.com",Collections.singleton(new Role ("ROLE_USER")));
        User parrot = new User("Попугай", "150", "parrot", Collections.singleton(new Role ("ROLE_USER")));
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");

        cat.setRoles(new HashSet<>(Set.of(admin)));
        dog.setRoles(new HashSet<>(Set.of(user)));
        parrot.setRoles(new HashSet<>(Set.of(admin,user)));

        userService.addRole(admin);
        userService.addRole(user);

        userService.addUser(cat);
        userService.addUser(dog);
        userService.addUser(parrot);

    }
}


