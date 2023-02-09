package com.andreyb34rus.JM_Task_3_1_1.controller;

import com.andreyb34rus.JM_Task_3_1_1.model.Role;
import com.andreyb34rus.JM_Task_3_1_1.model.User;
import com.andreyb34rus.JM_Task_3_1_1.repository.RoleRepository;
import com.andreyb34rus.JM_Task_3_1_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController (UserService userService,
                            RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("")
    public String showAdminPage(@ModelAttribute User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "usersList";
    }

    @GetMapping("/addUser")
    public String newUserPage(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", userService.getAllRoles());
        return "newUser";
    }

    @PostMapping("")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult,
                              Role role) {
        if (bindingResult.hasErrors()) {
            return "newUser";
        }
        //roleRepository.save(role);
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRolesList", user.getRoles());
        model.addAttribute("allRolesList", userService.getAllRoles());
        //model.addAttribute("roles", roleRepository.findById(id));
        return "editUser";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "editUser";
        }

        userService.update(user);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
