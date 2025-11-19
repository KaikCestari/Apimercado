package com.kaikdev.ApiMercado.Controller;

import com.kaikdev.ApiMercado.Model.Dto.LoginRequest;
import com.kaikdev.ApiMercado.Model.Dto.RefreshTokenRequest;
import com.kaikdev.ApiMercado.Model.Dto.RegisterRequest;
import com.kaikdev.ApiMercado.Model.Dto.TokenPair;
import com.kaikdev.ApiMercado.Service.Auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
          authService.registerUser(registerRequest);
          return ResponseEntity.ok("Registrado com sucesso");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        TokenPair tokenPair = authService.login(loginRequest);
        return ResponseEntity.ok(tokenPair);
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        TokenPair tokenPair = authService.refreshToken(request);
        return ResponseEntity.ok(tokenPair);
    }

}
