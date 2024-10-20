package com.openclassrooms.backend.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MessageDTO {
    private int id;
    private int rental_id;
    private int user_id;
    private String message;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}