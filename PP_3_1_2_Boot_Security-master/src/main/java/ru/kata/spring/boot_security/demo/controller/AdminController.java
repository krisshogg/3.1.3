package ru.kata.spring.boot_security.demo.controller;

import org.hibernate.AssertionFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private RoleService roleService;

    @Autowired
    public AdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("")
    public String showUserList(Model model) {
        model.addAttribute("users", roleService.findAllUsers());

        return "users";
    }

    @GetMapping(value = "/new")
    public String addUserForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles", roleService.findAllRoles());

        return "new";
    }

    @GetMapping("/{id}/edit")
    public String editUserForm(@PathVariable(value = "id", required = true) Long userId, Model model) {
        try {
            model.addAttribute("user", roleService.findUser(userId));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();

            return "redirect:/admin";
        }
        model.addAttribute("allRoles", roleService.findAllRoles());

        return "edit";
    }

    @PostMapping()
    public String saveOrUpdateUser(@ModelAttribute("user") User user,
                                   BindingResult bindingResult, Model model) {
        // Непонятно как избавиться от этого
        // Поймать в сервисе транзакционный эксепшн нельзя
        try {
            return roleService.saveUser(user, bindingResult, model) ? "redirect:/admin" : "user-form";
        } catch (AssertionFailure | UnexpectedRollbackException e) {
            return "user-form";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long userId) {
        roleService.deleteUser(userId);

        return "redirect:/admin";
    }
}
