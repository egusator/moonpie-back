package com.example.moonpie_back.api.dto;

import com.example.moonpie_back.core.entity.Color;
import com.example.moonpie_back.core.entity.Size;

import java.math.BigDecimal;
import java.util.Set;

public record UpdateItemDto(

        String name,

        String article,

        BigDecimal price,

        Set<String> colors,

        Set<String> sizes,

        Boolean toDisplay,

        String description,

        String category

) {
}
