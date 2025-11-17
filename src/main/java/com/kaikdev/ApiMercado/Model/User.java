package com.kaikdev.ApiMercado.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    @NotBlank(message = "Email Obrigatorio")
    private String email;
    @Column
    private String fullname;
    @Column
    private String username;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(Role role, String password, String username, String fullname,String email) {
        this.role = role;
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
    }
}
