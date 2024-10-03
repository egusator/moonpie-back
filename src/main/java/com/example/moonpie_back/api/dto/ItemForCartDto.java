package com.example.moonpie_back.api.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ItemForCartDto(

        Long id,

        String name,

        List<String> photoUrlList,

        Integer count,

        String size,
        CustomSize customSize,

        String color,

        BigDecimal finalPrice
) {
}
