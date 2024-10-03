package com.example.moonpie_back.api.dto;

import lombok.Builder;

@Builder
public record ClientContactInfo(
        String name,
        String email,
        String phone
) {
}
