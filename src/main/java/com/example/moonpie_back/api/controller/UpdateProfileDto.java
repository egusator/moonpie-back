package com.example.moonpie_back.api.controller;

public record UpdateProfileDto(
        String firstName,

        String middleName,

        String lastName,

        String email,

        String phoneNumber
) {
}
