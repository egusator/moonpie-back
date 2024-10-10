package com.example.moonpie_back.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record SignInRequest(
        @Schema(description = "Электронная почта")
        String email,

        @Schema(description = "Пароль")
        String password
) {
}
