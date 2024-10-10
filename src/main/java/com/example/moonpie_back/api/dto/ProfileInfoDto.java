package com.example.moonpie_back.api.dto;

import lombok.Builder;

@Builder
public record ProfileInfoDto(
    String firstName,
    String lastName,
    String middleName,
    String email,
    String phoneNumber

) {
}
