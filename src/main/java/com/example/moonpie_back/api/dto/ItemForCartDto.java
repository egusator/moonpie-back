package com.example.moonpie_back.api.dto;

import lombok.Builder;

@Builder
public record ItemForCartDto(

        String name,

        String photoUrl,

        String count,

        String size,

        String color,

        String finalPrice
) {
}
