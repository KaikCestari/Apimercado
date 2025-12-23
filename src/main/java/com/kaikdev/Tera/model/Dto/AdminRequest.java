package com.kaikdev.Tera.model.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminRequest {

    private String email;
    private String username;
    private String password;
}
