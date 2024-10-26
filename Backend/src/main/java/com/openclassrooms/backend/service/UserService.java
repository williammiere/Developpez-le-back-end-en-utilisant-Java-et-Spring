package com.openclassrooms.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String register(String email, String name, String password) {

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreated_at(LocalDateTime.now());

        userRepository.save(user);

        UserDetails userDetails = this.loadUserByEmail(email);

        return jwtService.generateToken(userDetails);
    }

    public String login(String email, String password) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            UserDetails userDetails = this.loadUserByEmail(email);
            return jwtService.generateToken(userDetails);
        } else {
            throw new IllegalArgumentException("Invalid credentials");
        }
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {

        Optional<User> user = findByEmail(email);

        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Email " + email + " not found");
        }
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }    
}
