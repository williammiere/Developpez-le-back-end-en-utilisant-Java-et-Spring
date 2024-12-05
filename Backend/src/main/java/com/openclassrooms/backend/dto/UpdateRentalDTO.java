package com.openclassrooms.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateRentalDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private float surface;
    @NotBlank
    private float price;
}
