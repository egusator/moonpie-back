package com.example.moonpie_back.api.dto;

public record CreateCategoryDto(
        String categoryName,
        String parentCategoryName
) {
}
