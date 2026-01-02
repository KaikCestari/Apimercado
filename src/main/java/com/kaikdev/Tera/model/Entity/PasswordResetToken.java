package com.kaikdev.Tera.model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int otp;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private boolean verified;


}
