package com.openclassrooms.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }    
}
