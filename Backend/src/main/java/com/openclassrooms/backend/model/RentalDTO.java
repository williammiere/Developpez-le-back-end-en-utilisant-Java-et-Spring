package com.openclassrooms.backend.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RentalDTO {
    private int id;
    private int owner_id;
    private String name;
    private String picture;
    private String description;
    private float surface;
    private float price;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}