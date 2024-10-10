package com.example.moonpie_back.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные для регистрации")
public record SignUpRequest(
        @Schema(description = "Имя")
        String fullName,


        @Schema(description = "Номер телефона")
        String phone,

        @Schema(description = "Электронная почта")
        String email,

        @Schema(description = "Пароль")
        String password
) {
}
