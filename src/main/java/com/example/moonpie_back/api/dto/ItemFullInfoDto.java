package com.example.moonpie_back.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ItemFullInfoDto(

        String name,

        List<String> colors,

        List<String> sizes,

        String photoUrl,

        String description,

        String category,

        String price
) {
}
