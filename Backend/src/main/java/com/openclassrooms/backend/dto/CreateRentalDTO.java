package com.openclassrooms.backend.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CreateRentalDTO {
    private String name;
    private String description;
    private float surface;
    private float price;
    private MultipartFile picture;
}
