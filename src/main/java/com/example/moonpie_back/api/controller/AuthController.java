package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ClientRegistrationDto;
import com.example.moonpie_back.core.service.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Регистрация и авторизация")
@Validated
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final ClientService clientService;

    @PostMapping(ApiPaths.REGISTER)
    public void registerNewClient(@RequestBody ClientRegistrationDto clientRegistrationDto) {
        clientService.registerNewClient(clientRegistrationDto);
    }

    @GetMapping(ApiPaths.AUTH)
    public String auth(
            @RequestParam String email,
            @RequestParam String password
    ) {
        return clientService.auth(email, password);
    }
}
