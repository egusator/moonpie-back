package com.example.moonpie_back.api.dto;

public record UpdateCategoryDto(
        String currentCategoryName,
        String newCategoryName
) {
}
