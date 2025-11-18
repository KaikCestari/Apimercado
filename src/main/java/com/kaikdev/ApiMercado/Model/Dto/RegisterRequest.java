package com.kaikdev.ApiMercado.Model.Dto;

import com.kaikdev.ApiMercado.Model.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String email;
    private String fullname;
    private String username;
    private String password;
    private Role role;
}
