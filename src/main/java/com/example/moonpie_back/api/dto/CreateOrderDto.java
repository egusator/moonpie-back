package com.example.moonpie_back.api.dto;

public record CreateOrderDto(

        String fullName,

        String city,

        String phoneNumber,

        String address
) {
}
