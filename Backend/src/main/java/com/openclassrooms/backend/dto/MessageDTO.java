package com.openclassrooms.backend.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageDTO {
  private int id;
  @NotBlank
  private int rental_id;
  @NotBlank
  private int user_id;
  @NotBlank
  private String message;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
