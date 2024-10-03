package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.OrderResponse;
import com.example.moonpie_back.api.dto.OrdersFilterDto;
import com.example.moonpie_back.core.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Заказы (админ)")
@Validated
@RestController
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping(ApiPaths.ADMIN_ORDER)
    public OrderResponse getOrders(OrdersFilterDto ordersFilterDto) {
        return new OrderResponse(orderService.getOrdersForAdmin(ordersFilterDto));
    }

    @PostMapping(ApiPaths.ADMIN_ORDER)
    public void changeOrderStatus(Long orderId, String CDEKOrderNumber) {
        orderService.changeOrderStatus(orderId, CDEKOrderNumber);
    }
}
