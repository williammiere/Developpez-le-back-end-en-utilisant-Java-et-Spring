package com.openclassrooms.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
  private int id;
  private int rental_id;
  private int user_id;
  private String message;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
