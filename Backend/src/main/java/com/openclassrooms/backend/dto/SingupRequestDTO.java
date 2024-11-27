package com.openclassrooms.backend.dto;

import lombok.Data;

@Data
public class SingupRequestDTO {
    private String email;
    private String name;
    private String password;

}
