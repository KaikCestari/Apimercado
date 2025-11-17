package com.kaikdev.ApiMercado.Model.Dto;

import com.kaikdev.ApiMercado.Model.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Email Obrigatorio")
    private String email;
    private String username;
    private String password;

}
