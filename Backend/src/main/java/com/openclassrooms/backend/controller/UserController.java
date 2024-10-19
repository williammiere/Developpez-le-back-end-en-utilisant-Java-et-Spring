package com.openclassrooms.backend.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User returnUser = user.get();
            returnUser.setId(0);
            returnUser.setPassword(null);
            returnUser.setEmail(null);
            return ResponseEntity.ok(returnUser);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/auth/register")
    public User register(@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("password") String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setCreated_at(LocalDateTime.now());
        return userService.save(user);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<User> login(@RequestParam("login") String email, @RequestParam("password") String password) {
        Optional<User> user = userService.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/auth/me")
    public User me(User user) {
        return user;
    }
}
