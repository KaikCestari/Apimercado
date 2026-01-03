package com.kaikdev.Tera.service.Auth;

import com.kaikdev.Tera.exception.BadRequestException;
import com.kaikdev.Tera.exception.ConflictException;
import com.kaikdev.Tera.exception.ResourceNotFoundException;
import com.kaikdev.Tera.exception.UnauthorizedException;
import com.kaikdev.Tera.model.Dto.*;
import com.kaikdev.Tera.model.Entity.PasswordResetToken;
import com.kaikdev.Tera.model.Entity.User;
import com.kaikdev.Tera.model.Enum.Role;
import com.kaikdev.Tera.repository.PasswordResetRepository;
import com.kaikdev.Tera.repository.UserRepository;
import com.kaikdev.Tera.service.EmailService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;


    @Transactional
    public void registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ConflictException("Username já cadastrado");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ConflictException("Email já cadastrado");
        }

        User user = User
                .builder()
                .email(registerRequest.getEmail())
                .fullname(registerRequest.getFullname())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.ROLE_USUARIO)
                .build();
        userRepository.save(user);
    }


    public TokenPair login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtService.generateTokenPair(authentication);

    }

    public TokenPair refreshToken(@Valid RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();

        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new BadRequestException("Refresh token inválido");
        }

        String user = jwtService.extractUsernameFromToken(refreshToken);
        if (user == null) {
            throw new UnauthorizedException("Token sem usuário associado");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

        if (userDetails == null) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        String accessToken = jwtService.generateAccessToken(authentication);
        return new TokenPair(accessToken, refreshToken);

    }


    public void forgotPassword(String email ) {

        int otp = otpGenerator();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not valid "));

        emailService.send(email, "Otp for forgot password", "This is the otp :" + otp);

        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .otp(otp)
                .expirationDate(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        passwordResetRepository.save(passwordResetToken);
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }


    public void verifyOtp(int otp, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PasswordResetToken token = passwordResetRepository
                .findByOtpAndUser(otp,user)
                .orElseThrow(() -> new RuntimeException("OTP inválido"));

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expirada");
        }

        token.setVerified(true);
        passwordResetRepository.save(token);
    }
    public void changePassword(ChangePassword dto) {

        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PasswordResetToken token = passwordResetRepository
                .findByUser(user)
                .orElseThrow(() -> new RuntimeException("Recuperação não iniciada"));

        if (!token.isVerified()) {
            throw new RuntimeException("OTP ainda não verificada");
        }

        if (token.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Processo expirado");
        }


        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);

        passwordResetRepository.delete(token); // invalida
    }


}



