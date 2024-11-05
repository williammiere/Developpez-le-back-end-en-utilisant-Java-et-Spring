package com.openclassrooms.backend.service;

import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

  public String register(String email, String name, String password) {

    Optional<User> existingUser = userRepository.findByEmail(email);

    if (!existingUser.isEmpty()) {
      throw new IllegalArgumentException("Email already exists");
    }

    User user = new User();
    user.setEmail(email);
    user.setName(name);
    user.setPassword(passwordEncoder.encode(password));
    user.setCreated_at(LocalDateTime.now());

    userRepository.save(user);

    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(email).password(user.getPassword()).build();

    return jwtService.generateToken(userDetails);
  }

  public String login(String email, String password) {

    Optional<User> user = userRepository.findByEmail(email);

    if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
      UserDetails userDetails = org.springframework.security.core.userdetails.User.builder().username(email).password(user.get().getPassword()).build();
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

  public void deleteById(int id) {
    userRepository.deleteById(id);
  }

  public boolean deleteAll() {
    userRepository.deleteAll();
    return true;
  }
}
