package com.example.moonpie_back.api.dto;

public record ClientRegistrationDto(

        String firstName,

        String lastName,

        String middleName,

        String email,

        String password
) {
}
