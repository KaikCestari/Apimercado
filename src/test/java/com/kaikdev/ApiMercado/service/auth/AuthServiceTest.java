package com.kaikdev.ApiMercado.service.auth;

import com.kaikdev.ApiMercado.exception.BadRequestException;
import com.kaikdev.ApiMercado.exception.ConflictException;
import com.kaikdev.ApiMercado.exception.ResourceNotFoundException;
import com.kaikdev.ApiMercado.exception.UnauthorizedException;
import com.kaikdev.ApiMercado.model.Dto.RefreshTokenRequest;
import com.kaikdev.ApiMercado.model.Dto.RegisterRequest;
import com.kaikdev.ApiMercado.model.Dto.TokenPair;
import com.kaikdev.ApiMercado.model.Entity.User;
import com.kaikdev.ApiMercado.model.Enum.Role;
import com.kaikdev.ApiMercado.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;

    @BeforeEach
    void setup() {
        registerRequest = RegisterRequest.builder()
                .email("user@test.com")
                .fullname("User Test")
                .username("user")
                .password("123456")
                .role(Role.ROLE_ADMIN)
                .build();
    }

    @Test
    void registerUser_deveLancarExcecaoQuandoUsernameJaExiste() {
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(ConflictException.class, () -> authService.registerUser(registerRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerUser_deveSalvarSenhaCodificada() {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");

        authService.registerUser(registerRequest);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getPassword()).isEqualTo("encoded");
    }

    @Test
    void refreshToken_deveLancarBadRequestQuandoTokenNaoEhRefresh() {
        RefreshTokenRequest request = new RefreshTokenRequest("token");
        when(jwtService.isRefreshToken("token")).thenReturn(false);

        assertThrows(BadRequestException.class, () -> authService.refreshToken(request));
    }

    @Test
    void refreshToken_deveLancarUnauthorizedQuandoSemUsuario() {
        RefreshTokenRequest request = new RefreshTokenRequest("token");
        when(jwtService.isRefreshToken("token")).thenReturn(true);
        when(jwtService.extractUsernameFromToken("token")).thenReturn(null);

        assertThrows(UnauthorizedException.class, () -> authService.refreshToken(request));
    }

    @Test
    void refreshToken_deveLancarNotFoundQuandoUserDetailsNulo() {
        RefreshTokenRequest request = new RefreshTokenRequest("token");
        when(jwtService.isRefreshToken("token")).thenReturn(true);
        when(jwtService.extractUsernameFromToken("token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> authService.refreshToken(request));
    }

    @Test
    void refreshToken_deveGerarNovoAccessToken() {
        RefreshTokenRequest request = new RefreshTokenRequest("token");
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername("user")
                .password("pass")
                .authorities("ROLE_ADMIN")
                .build();
        when(jwtService.isRefreshToken("token")).thenReturn(true);
        when(jwtService.extractUsernameFromToken("token")).thenReturn("user");
        when(userDetailsService.loadUserByUsername("user")).thenReturn(userDetails);
        when(jwtService.generateAccessToken(any(Authentication.class))).thenReturn("newAccess");

        TokenPair tokenPair = authService.refreshToken(request);

        assertThat(tokenPair.getAcessToken()).isEqualTo("newAccess");
        assertThat(tokenPair.getRefreshToken()).isEqualTo("token");
    }
}
