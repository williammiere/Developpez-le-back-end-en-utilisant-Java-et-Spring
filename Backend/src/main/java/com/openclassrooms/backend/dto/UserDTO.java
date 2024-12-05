package com.openclassrooms.backend.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {
  private int id;
  @NotBlank
  private String name;
  @NotBlank
  private String password;
  @NotBlank
  private String email;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
