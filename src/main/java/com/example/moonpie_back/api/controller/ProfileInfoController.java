package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ProfileInfoDto;
import com.example.moonpie_back.core.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Регистрация и авторизация")
@Validated
@RestController
@RequiredArgsConstructor
public class ProfileInfoController {
    private final ClientService clientService;

    @Operation(summary = "Авторизация пользователя")
    @GetMapping(ApiPaths.PROFILE_INFO)
    public ProfileInfoDto getProfileInfo() {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clientService.getProfileInfo(clientId);

    }

    @Operation(summary = "Авторизация пользователя")
    @PutMapping(ApiPaths.PROFILE_INFO)
    public void fullUpdateProfileInfo() {

    }

    @Operation(summary = "Авторизация пользователя")
    @PatchMapping(ApiPaths.PROFILE_INFO)
    public void partialUpdateProfileInfo() {

    }


}
