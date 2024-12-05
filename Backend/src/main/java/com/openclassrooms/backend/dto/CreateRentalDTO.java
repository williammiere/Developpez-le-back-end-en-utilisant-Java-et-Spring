package com.openclassrooms.backend.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRentalDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private float surface;
    @NotBlank
    private float price;
    @NotBlank
    private MultipartFile picture;
}
