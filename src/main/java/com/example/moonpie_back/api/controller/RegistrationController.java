package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ClientRegistrationDto;
import com.example.moonpie_back.core.service.ClientService;
import com.example.moonpie_back.core.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Регистрация и авторизация")
@Validated
@RestController
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @Operation(summary = "Регистрация пользователя без привелегий")
    @PostMapping(ApiPaths.CLIENT_SIGNUP)
    public void registerClient(@RequestBody ClientRegistrationDto request) {
        registrationService.registerNewClient(request);
    }


}
