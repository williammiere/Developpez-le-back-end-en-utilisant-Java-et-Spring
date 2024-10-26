package com.openclassrooms.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.model.UserDTO;
import com.openclassrooms.backend.model.modelMapper.UserMapper;
import com.openclassrooms.backend.service.UserService;

@RestController
@CrossOrigin("localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/{id}")
    @CrossOrigin("localhost:4200")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            User returnUser = user.get();
            returnUser.setId(0);
            returnUser.setPassword(null);
            returnUser.setEmail(null);
            return ResponseEntity.ok(userMapper.toUserDTO(returnUser));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/auth/register")
    @CrossOrigin("localhost:4200")
    public ResponseEntity<?> register(@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("password") String password) {
        try{
        String jwtToken = userService.register(email, name, password);
        return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/auth/login")
    @CrossOrigin("localhost:4200")
    public ResponseEntity<?> login(@RequestParam("login") String email, @RequestParam("password") String password) {
        try{
        String jwtToken = userService.login(email, password);
        return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/auth/me")
    @CrossOrigin("http://localhost:4200")
    public ResponseEntity<UserDTO> me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		String email = authentication.getName();

        Optional<User> user = userService.findByEmail(email);

        if(user.isPresent()) {
            return ResponseEntity.ok(userMapper.toUserDTO(user.get()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/auth/deleteAll")
    public String deleteAll() {
        userService.deleteAll();
        return "All users deleted";
    }
    
}
