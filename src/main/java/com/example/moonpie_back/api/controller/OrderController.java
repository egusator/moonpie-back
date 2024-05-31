package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.CreateOrderDto;
import com.example.moonpie_back.core.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Заказы")
@Validated
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(ApiPaths.ORDER)
    public void CreateOrder(@RequestBody CreateOrderDto createOrderDto) {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        orderService.createOrder(createOrderDto, clientId);
    }
}
