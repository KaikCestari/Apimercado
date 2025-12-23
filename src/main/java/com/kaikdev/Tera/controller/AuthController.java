package com.kaikdev.Tera.controller;

import com.kaikdev.Tera.model.Dto.*;
import com.kaikdev.Tera.model.Entity.User;
import com.kaikdev.Tera.repository.UserRepository;
import com.kaikdev.Tera.service.Auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        authService.registerUser(registerRequest);
        return ResponseEntity.ok("Registrado Com sucesso");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        TokenPair tokenPair = authService.login(loginRequest);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/verifyMail/{email}")
    public ResponseEntity<String> forgotPassword(
            @PathVariable String email) {
        authService.forgotPassword(email);
        return ResponseEntity.ok("Email Sent for verification!");

    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePassword(@RequestBody ChangePassword changePassword, @PathVariable String email) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            throw new RuntimeException("Senhas nao conferem!");
        }

        String encodePassword = passwordEncoder.encode(changePassword.password());

        userRepository.updatePassword(email, encodePassword);
        return ResponseEntity.ok("Senha Altera Com Sucesso!");
    }


    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable int otp, @PathVariable String email) {
        authService.verifyOtp(otp, email);
        return ResponseEntity.ok("Otp verificada");
    }
}



