package com.openclassrooms.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.backend.dto.LoginRequestDTO;
import com.openclassrooms.backend.dto.SingupRequestDTO;
import com.openclassrooms.backend.dto.TokenResponseDTO;
import com.openclassrooms.backend.dto.UserAuthResponseDTO;
import com.openclassrooms.backend.dto.UserDTO;
import com.openclassrooms.backend.modelMapper.UserMapper;
import com.openclassrooms.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

  @GetMapping("/user/{id}")
  public ResponseEntity<UserAuthResponseDTO> getUser(@PathVariable int id) {

    UserDTO user = userMapper.toUserDTO(userService.findById(id));

    UserAuthResponseDTO userResponse = new UserAuthResponseDTO();
    userResponse.setId(user.getId());
    userResponse.setEmail(user.getEmail());
    userResponse.setName(user.getName());
    userResponse.setCreated_at(user.getCreated_at());
    userResponse.setUpdated_at(user.getUpdated_at());
    
      return ResponseEntity.ok(userResponse);
  }

  @PostMapping("/auth/register")
  public ResponseEntity<TokenResponseDTO> register(
          @Valid @RequestBody SingupRequestDTO singupRequestDTO) {

      String jwtToken = userService.register(singupRequestDTO.getEmail(), singupRequestDTO.getName(), singupRequestDTO.getPassword());
      TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
      tokenResponseDTO.setToken(jwtToken);
      return ResponseEntity.ok(tokenResponseDTO);
  }

  @PostMapping("/auth/login")
  public ResponseEntity<?> login(
          @Valid @RequestBody LoginRequestDTO loginRequestDTO) {

      String jwtToken = userService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
      TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();
      tokenResponseDTO.setToken(jwtToken);
      return ResponseEntity.ok(tokenResponseDTO);
  }

  @GetMapping("/auth/me")
  public ResponseEntity<UserAuthResponseDTO> me() {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    UserDTO user = userMapper.toUserDTO(userService.findByEmail(email));

    UserAuthResponseDTO userResponse = new UserAuthResponseDTO();
    userResponse.setId(user.getId());
    userResponse.setEmail(email);
    userResponse.setName(user.getName());
    userResponse.setCreated_at(user.getCreated_at());
    userResponse.setUpdated_at(user.getUpdated_at());

    return ResponseEntity.ok(userResponse);

  }

}
