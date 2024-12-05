package com.openclassrooms.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMessageDTO {
    @NotBlank
    private int rental_id;
    @NotBlank
    private int user_id;
    @NotBlank
    private String message;
}
