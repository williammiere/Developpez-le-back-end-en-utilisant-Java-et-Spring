package com.openclassrooms.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(int id) {
        return userService.findById(id).get();
    }

    @PostMapping("/auth/register")
    public User register(User user) {
        return userService.save(user);
    }

    @PostMapping("/auth/login")
    public User login(User user) {
        return user;
    }

    @GetMapping("/auth/me")
    public User me(User user) {
        return user;
    }
}
