package com.andreyb34rus.JM_Task_3_1_1.configs;

import com.andreyb34rus.JM_Task_3_1_1.model.Role;
import com.andreyb34rus.JM_Task_3_1_1.model.User;
import com.andreyb34rus.JM_Task_3_1_1.repository.RoleRepository;
import com.andreyb34rus.JM_Task_3_1_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.util.HashSet;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void run(ApplicationArguments args) {

        Role userRole = new Role("ROLE_USER");
        Role adminRole = new Role("ROLE_ADMIN");

        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        userRepository.save(new com.andreyb34rus.JM_Task_3_1_1.model.User("user", "user", "fdfok@fifi.com", new HashSet<Role>() {{
            add(userRole);
        }}));
        userRepository.save(new User("admin", "admin", "fdfok@fifi.com", new HashSet<Role>() {{
            add(userRole);
            add(adminRole);
        }}));
    }
}

