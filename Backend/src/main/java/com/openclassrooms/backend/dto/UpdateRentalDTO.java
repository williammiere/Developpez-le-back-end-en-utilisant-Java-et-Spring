package com.openclassrooms.backend.dto;

import lombok.Data;

@Data
public class UpdateRentalDTO {
    private String name;
    private String description;
    private float surface;
    private float price;
}
