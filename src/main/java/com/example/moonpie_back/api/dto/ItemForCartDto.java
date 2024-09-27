package com.example.moonpie_back.api.dto;

import lombok.Builder;

@Builder
public record ItemForCartDto(

        Long id,

        String name,

        String photoUrl,

        String count,

        String size,
        CustomSize customSize,

        String color,

        String finalPrice
) {
}
