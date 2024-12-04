package com.openclassrooms.backend.modelMapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.backend.dto.UserDTO;
import com.openclassrooms.backend.model.User;

@Component
public class UserMapper {

  public UserDTO toUserDTO(User user) {
    UserDTO userDTO = new UserDTO();
    userDTO.setId(user.getId());
    userDTO.setName(user.getName());
    userDTO.setPassword(user.getPassword());
    userDTO.setEmail(user.getEmail());
    userDTO.setCreated_at(user.getCreated_at());
    userDTO.setUpdated_at(user.getUpdated_at());
    return userDTO;
  }
}
