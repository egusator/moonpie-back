package com.example.moonpie_back.api.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ItemForCatalogDto(
        String name,
        List<String> size,
        String price,
        List<String> color
) {
}
