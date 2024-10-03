package com.example.moonpie_back.api.dto;

import com.example.moonpie_back.core.entity.CartItem;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.entity.OrderStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderFullInfoDto(
    Long id,

    ClientContactInfo clientContactInfo,

    OrderStatus orderStatus,

    List<ItemForCartDto> cartItems,

    String address,

    String comment,

    String phoneNumber,

    String city,

    String fullName,

    String CDEKOrderNumber
) {
}
