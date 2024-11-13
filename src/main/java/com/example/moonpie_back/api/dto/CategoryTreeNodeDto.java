package com.example.moonpie_back.api.dto;

import java.util.List;

public record CategoryTreeNodeDto(
        String name,
        List<CategoryTreeNodeDto> children
) {
}
