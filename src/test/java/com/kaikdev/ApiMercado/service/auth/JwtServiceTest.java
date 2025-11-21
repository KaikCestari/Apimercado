package com.kaikdev.ApiMercado.service.auth;

import com.kaikdev.ApiMercado.exception.UnauthorizedException;
import com.kaikdev.ApiMercado.model.Dto.TokenPair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        String secret = Base64.getEncoder()
                .encodeToString("super-secret-key-for-tests-1234567890".getBytes(StandardCharsets.UTF_8));
        ReflectionTestUtils.setField(jwtService, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", 3_600_000L);
        ReflectionTestUtils.setField(jwtService, "refreshExpirationMs", 7_200_000L);
    }

    @Test
    void generateTokenPair_deveGerarTokensValidos() {
        Authentication authentication = buildAuthentication("user");

        TokenPair pair = jwtService.generateTokenPair(authentication);

        assertThat(pair.getAcessToken()).isNotBlank();
        assertThat(pair.getRefreshToken()).isNotBlank();
        assertThat(jwtService.validateTokenForUser(pair.getAcessToken(), (UserDetails) authentication.getPrincipal()))
                .isTrue();
        assertThat(jwtService.isRefreshToken(pair.getRefreshToken())).isTrue();
    }

    @Test
    void isRefreshToken_deveRetornarFalseParaAccessToken() {
        Authentication authentication = buildAuthentication("user");

        TokenPair pair = jwtService.generateTokenPair(authentication);

        assertThat(jwtService.isRefreshToken(pair.getAcessToken())).isFalse();
    }

    @Test
    void extractUsernameFromToken_deveLancarUnauthorizedParaTokenInvalido() {
        assertThrows(UnauthorizedException.class, () -> jwtService.extractUsernameFromToken("invalid.token"));
    }

    private Authentication buildAuthentication(String username) {
        UserDetails userDetails = User.withUsername(username)
                .password("password")
                .authorities("ROLE_USER")
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
