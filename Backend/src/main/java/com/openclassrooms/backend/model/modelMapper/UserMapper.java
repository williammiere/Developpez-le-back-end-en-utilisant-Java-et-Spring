package com.openclassrooms.backend.model.modelMapper;

import org.springframework.stereotype.Component;

import com.openclassrooms.backend.model.User;
import com.openclassrooms.backend.model.UserDTO;

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

    public User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setCreated_at(userDTO.getCreated_at());
        user.setUpdated_at(userDTO.getUpdated_at());
        return user;
    }
}
