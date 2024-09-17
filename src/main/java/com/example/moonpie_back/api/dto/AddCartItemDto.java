package com.example.moonpie_back.api.dto;

public record AddCartItemDto(

        String itemName,

        String size,

        CustomSize customSize,

        String color,

        Integer quantity
) {
}
