package com.kaikdev.ApiMercado.service.auth;

import com.kaikdev.ApiMercado.exception.BadRequestException;
import com.kaikdev.ApiMercado.exception.ConflictException;
import com.kaikdev.ApiMercado.exception.ResourceNotFoundException;
import com.kaikdev.ApiMercado.exception.UnauthorizedException;
import com.kaikdev.ApiMercado.model.Dto.LoginRequest;
import com.kaikdev.ApiMercado.model.Dto.RefreshTokenRequest;
import com.kaikdev.ApiMercado.model.Dto.RegisterRequest;
import com.kaikdev.ApiMercado.model.Dto.TokenPair;
import com.kaikdev.ApiMercado.model.Entity.User;
import com.kaikdev.ApiMercado.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;

    private  final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Transactional
    public void registerUser(RegisterRequest registerRequest){
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ConflictException("Username já cadastrado");
        }
            User user = User
                    .builder()
                    .email(registerRequest.getEmail())
                    .fullname(registerRequest.getFullname())
                    .username(registerRequest.getUsername())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(registerRequest.getRole())
                    .build();
            userRepository.save(user);
        }

    public TokenPair login(LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return  jwtService.generateTokenPair(authentication);

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
}
