package com.openclassrooms.backend.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RentalDTO {
  @NotBlank
  private int id;
  @NotBlank
  private int owner_id;
  @NotBlank
  private String name;
  private String picture;
  @NotBlank
  private String description;
  @NotBlank
  private float surface;
  @NotBlank
  private float price;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
