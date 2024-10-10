package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.AdminRegistrationDto;
import com.example.moonpie_back.core.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Регистрация (админ)")
@Validated
@RestController
@RequiredArgsConstructor
public class AdminRegistrationController {

    private final ClientService clientService;

    @Operation(summary = "Регистрация сотрудника или админа")
    @PostMapping(ApiPaths.ADMIN_REGISTER)
    public void registerNewEmployeeOrAdmin(AdminRegistrationDto adminRegistrationDto) {
        clientService.registerNewEmployeeOrAdmin(adminRegistrationDto);
    }
}
