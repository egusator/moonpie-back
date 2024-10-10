package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.JwtAuthenticationResponse;
import com.example.moonpie_back.api.dto.SignInRequest;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.repository.AuthorityRepository;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;

    private final PasswordService passwordService;

    private final ClientService userService;

    private final AuthenticationManager authenticationManager;

    private final Map<String, String> refreshStorage = new HashMap<>();

    private final AuthorityRepository authorityRepository;

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.email(),
                        signInRequest.password()
                )
        );

        Client user = (Client) userService
                .getClientByEmail(signInRequest.email());

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        refreshStorage.put(user.getEmail(), refreshToken);
        return new JwtAuthenticationResponse(accessToken, refreshToken);
    }


    public JwtAuthenticationResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Client user = userService.getClientByEmail(email);
                final String accessToken = jwtService.generateAccessToken(user);
                return new JwtAuthenticationResponse(accessToken, null);
            }
        }
        return new JwtAuthenticationResponse(null, null);
    }

    public JwtAuthenticationResponse refresh(@NonNull String refreshToken) {
        if (jwtService.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtService.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Client user = userService.getClientByEmail(login);
                final String accessToken = jwtService.generateAccessToken(user);
                final String newRefreshToken = jwtService.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtAuthenticationResponse(accessToken, newRefreshToken);
            }
        }
        throw new RuntimeException("Invalid refresh token");
    }
}
