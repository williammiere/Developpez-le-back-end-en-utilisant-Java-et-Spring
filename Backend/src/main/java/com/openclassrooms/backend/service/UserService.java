package com.openclassrooms.backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.backend.dto.UserDTO;
import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.modelMapper.UserMapper;
import com.openclassrooms.backend.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
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

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private UserMapper userMapper;

  public String register(String email, String name, String password) {
    User existingUser = userRepository.findByEmail(email);

    if (existingUser != null) {
      throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    user.setEmail(email);
    user.setName(name);
    user.setPassword(passwordEncoder.encode(password));
    user.setCreated_at(LocalDateTime.now());

    userRepository.save(user);

    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        .username(email)
        .password(user.getPassword())
        .build();

    return jwtService.generateToken(userDetails);
  }

  public String login(String email, String password) {
    User user = userRepository.findByEmail(email);

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("Invalid credentials");
    }

    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
        .username(email)
        .password(user.getPassword())
        .build();

    return jwtService.generateToken(userDetails);
  }

  public UserDTO save(User user) {
    return userMapper.toUserDTO(userRepository.save(user));
  }

  public UserDTO findById(int id) {
    User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    return userMapper.toUserDTO(user);
  }

  public UserDTO findByEmail(String email) {
    User user = userRepository.findByEmail(email);
    return userMapper.toUserDTO(user);
  }

  public void deleteById(int id) {
    userRepository.deleteById(id);
  }
}