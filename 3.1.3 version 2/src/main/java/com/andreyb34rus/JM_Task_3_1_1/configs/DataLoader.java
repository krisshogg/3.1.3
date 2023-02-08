package com.andreyb34rus.JM_Task_3_1_1.configs;

import com.andreyb34rus.JM_Task_3_1_1.model.Role;
import com.andreyb34rus.JM_Task_3_1_1.model.User;
import com.andreyb34rus.JM_Task_3_1_1.repository.RoleRepository;
import com.andreyb34rus.JM_Task_3_1_1.repository.UserRepository;
import com.andreyb34rus.JM_Task_3_1_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import javax.xml.crypto.Data;
import java.util.HashSet;

@Component
public class DataLoader implements ApplicationRunner {
    private UserService userService;
    private final RoleRepository roleRepository;


    @Autowired
    public DataLoader (UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public void run(ApplicationArguments args) {

        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        userService.save(new User("user", "user", "fdfok@fifi.com", new HashSet<Role>() {{
            add(userRole);
        }}));
        userService.save(new User("admin", "admin", "fdfok@fifi.com", new HashSet<Role>() {{
            add(userRole);
            add(adminRole);
        }}));
    }
}
/* private UserService userService;

    @Autowired
    public DataLoader (UserService userService) {
        this.userService = userService;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        userService.save(new com.andreyb34rus.JM_Task_3_1_1.model.User("user", "user", "fdfok@fifi.com", new HashSet<Role>() {{
            add(userRole);
        }}));
        userService.save(new User("admin", "admin", "fdfok@fifi.com", new HashSet<Role>() {{
            add(userRole);
            add(adminRole);
        }}));
    }*/




