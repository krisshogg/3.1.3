package com.andreyb34rus.JM_Task_3_1_1.service;

import com.andreyb34rus.JM_Task_3_1_1.model.Role;
import com.andreyb34rus.JM_Task_3_1_1.model.User;

import java.util.List;

public interface UserService {

    User getUserByName(String name);

    User getUserById(int id);

    List<User> getAllUsers();

    void save(User user);

    void update(User updatedUser);

    void delete(int id);

    List<Role> getAllRoles();

}
