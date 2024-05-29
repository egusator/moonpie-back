package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.CreateOrderDto;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.entity.Order;
import com.example.moonpie_back.core.entity.OrderStatus;
import com.example.moonpie_back.core.repository.ClientRepository;
import com.example.moonpie_back.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    public void createOrder(CreateOrderDto createOrderDto, Long clientId) {
        Client client = clientRepository.findClientById(clientId);
        Set<Order> orders = client.getOrders();
        Order currentOrder = orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.CREATED)
                .toList()
                .get(0);
        currentOrder.setOrderStatus(OrderStatus.IN_PROCESS);
        currentOrder.setCity(createOrderDto.city());
        currentOrder.setAddress(createOrderDto.address());
        currentOrder.setFullName(createOrderDto.fullName());
        currentOrder.setPhoneNumber(createOrderDto.phoneNumber());
        orderRepository.save(currentOrder);
    }
}
