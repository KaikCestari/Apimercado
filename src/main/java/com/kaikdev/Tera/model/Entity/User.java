package com.kaikdev.Tera.model.Entity;

import com.kaikdev.Tera.model.Enum.Role;
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
    @Column(unique = true)
    private String email;
    @Column
    private String fullname;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user")
    private PasswordResetToken passwordResetToken;

    @OneToOne
    private Gastos gastos;

    public User(Role role, String password, String username, String fullname,String email) {
        this.role = role;
        this.password = password;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
    }


    public User(String email, String username, String password, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public void setEnabled(boolean b)
    {
    }
}
