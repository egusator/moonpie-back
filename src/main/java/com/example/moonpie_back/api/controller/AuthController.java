package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.JwtAuthenticationResponse;
import com.example.moonpie_back.api.dto.RefreshJwtRequest;
import com.example.moonpie_back.api.dto.SignInRequest;
import com.example.moonpie_back.core.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Регистрация и авторизация")
@Validated
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Авторизация пользователя")
    @PostMapping(ApiPaths.LOGIN)
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        JwtAuthenticationResponse jwt = authenticationService.signIn(request);
        return jwt;
    }

    @Operation(summary = "Получение нового токена")
    @PostMapping(ApiPaths.TOKEN)
    public ResponseEntity<JwtAuthenticationResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtAuthenticationResponse token = authenticationService.getAccessToken(request.refreshToken());
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "Получение нового рефреш токена")
    @PostMapping(ApiPaths.REFRESH)
    public ResponseEntity<JwtAuthenticationResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtAuthenticationResponse token = authenticationService.refresh(request.refreshToken());
        return ResponseEntity.ok(token);
    }
}
