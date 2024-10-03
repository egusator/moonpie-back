package com.example.moonpie_back.api.dto;

import com.example.moonpie_back.core.entity.Order;

import java.util.List;

public record OrderResponse(
    List<OrderFullInfoDto> orders
) {
}
