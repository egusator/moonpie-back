package com.example.moonpie_back.api.dto;

import java.math.BigDecimal;
import java.util.Set;

public record CreateItemDto(

        String name,

        String article,

        BigDecimal price,

        Set<String> colors,

        Set<String> sizes,

        String description,

        String category
) {
}
