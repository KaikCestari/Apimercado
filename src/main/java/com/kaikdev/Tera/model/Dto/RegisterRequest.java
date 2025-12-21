package com.kaikdev.Tera.model.Dto;

import com.kaikdev.Tera.model.Enum.Role;
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
}
