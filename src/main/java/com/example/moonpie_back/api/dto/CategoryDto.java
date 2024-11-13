package com.example.moonpie_back.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CategoryDto(
        String name
) {
}
