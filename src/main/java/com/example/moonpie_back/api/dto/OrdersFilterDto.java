package com.example.moonpie_back.api.dto;

import com.example.moonpie_back.core.entity.OrderStatus;

public record OrdersFilterDto(
        String clientName,
        OrderStatus status,
        String itemName,
        Long orderId
) {
}
