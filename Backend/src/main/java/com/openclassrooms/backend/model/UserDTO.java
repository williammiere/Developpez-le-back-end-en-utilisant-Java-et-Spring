package com.openclassrooms.backend.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String name;
    private String password;
    private String email;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}