package com.andreyb34rus.JM_Task_3_1_1.service;

import com.andreyb34rus.JM_Task_3_1_1.model.Role;
import com.andreyb34rus.JM_Task_3_1_1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {

    User getUserByName(String name);

    User getUserById(int id);

    List<User> getAllUsers();

    void save(User user);

    void update(User user);

    void delete(int id);

    void addRoles(Role role);

    void addUser(User user);


}
