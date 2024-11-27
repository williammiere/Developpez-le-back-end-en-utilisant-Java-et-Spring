package com.openclassrooms.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
  private int id;
  private String name;
  private String password;
  private String email;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
