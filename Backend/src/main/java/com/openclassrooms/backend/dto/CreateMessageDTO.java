package com.openclassrooms.backend.dto;

import lombok.Data;

@Data
public class CreateMessageDTO {
    private int rental_id;
    private int user_id;
    private String message;
}
