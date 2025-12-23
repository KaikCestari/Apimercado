package com.kaikdev.Tera.repository;

import com.kaikdev.Tera.model.Entity.PasswordResetToken;

import com.kaikdev.Tera.model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken,Long> {

    @Query("SELECT p FROM PasswordResetToken p WHERE p.otp = :otp AND p.user = :user")
    Optional<PasswordResetToken> findByOtpAndUser(@Param("otp") int otp, @Param("user") User user);
}
