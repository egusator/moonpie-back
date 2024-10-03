package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.*;
import com.example.moonpie_back.core.entity.Client;
import com.example.moonpie_back.core.entity.Order;
import com.example.moonpie_back.core.entity.OrderStatus;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.exception.EventInfo;
import com.example.moonpie_back.core.repository.ClientRepository;
import com.example.moonpie_back.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public List<OrderFullInfoDto> getOrdersForAdmin(OrdersFilterDto ordersFilterDto) {
        return orderRepository.findAll().stream()
                .filter(
                        order -> ordersFilterDto.status() == null ||
                                order.getOrderStatus() == ordersFilterDto.status()
                )
                .filter(
                        order -> ordersFilterDto.clientName() == null ||
                                order.getClient().getName().equals(ordersFilterDto.clientName())
                )
                .filter(
                        order -> ordersFilterDto.orderId() == null ||
                                order.getId() == ordersFilterDto.orderId()
                )
                .filter(order -> ordersFilterDto.itemName() == null ||
                        order.getCartItems().stream()
                                .map(
                                        cartItem -> cartItem.getItem().getName()
                                )
                                .toList()
                                .contains(ordersFilterDto.itemName())
                )
                .map(order -> OrderFullInfoDto.builder()
                        .id(order.getId())
                        .clientContactInfo(
                                ClientContactInfo.builder()
                                        .name(order.getClient().getName())
                                        .email(order.getClient().getEmail())
                                        .phone(order.getClient().getPhoneNumber())
                                        .build()
                        )
                        .orderStatus(order.getOrderStatus())
                        .CDEKOrderNumber(order.getCDEKOrderNumber())
                        .address(order.getAddress())
                        .comment(order.getComment())
                        .phoneNumber(order.getPhoneNumber())
                        .city(order.getCity())
                        .cartItems(order.getCartItems().stream()
                                .map(
                                        cartItem -> ItemForCartDto.builder()
                                                .id(cartItem.getId())
                                                .name(cartItem.getItem().getName())
                                                .photoUrlList(cartItem.getItem().getPhotoUrlList().stream().toList())
                                                .count(cartItem.getCount())
                                                .size(cartItem.getSize().getValue())
                                                .color(cartItem.getColor().getValue())
                                                .finalPrice(cartItem.getPrice())
                                                .build()
                                ).toList()
                        )
                        .fullName(order.getFullName())
                        .build()
                )
                .toList();
    }

    public void changeOrderStatus(Long orderId, String CDEKOrderNumber) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        optionalOrder.ifPresentOrElse(
                order -> {
                    order.setCDEKOrderNumber(CDEKOrderNumber);

                    order.setOrderStatus(OrderStatus.COMPLETED);

                    orderRepository.save(order);
                },
                () -> {
                    throw new BusinessException(
                            UserAuthEvent.ORDER_WITH_THIS_ID_IS_NOT_FOUND,
                            "Order with this ID is not found!"
                    );
                }
        );
    }

    public List<OrderFullInfoDto> getOrdersForClient(Long clientId) {
        Client client = clientRepository.findClientById(clientId);
        Set<Order> orders = client.getOrders();
        return orders.stream().map(
                order -> OrderFullInfoDto.builder()
                        .id(order.getId())
                        .clientContactInfo(
                                ClientContactInfo.builder()
                                        .name(order.getClient().getName())
                                        .email(order.getClient().getEmail())
                                        .phone(order.getClient().getPhoneNumber())
                                        .build()
                        )
                        .orderStatus(order.getOrderStatus())
                        .CDEKOrderNumber(order.getCDEKOrderNumber())
                        .address(order.getAddress())
                        .comment(order.getComment())
                        .phoneNumber(order.getPhoneNumber())
                        .city(order.getCity())
                        .cartItems(order.getCartItems().stream()
                                .map(
                                        cartItem -> ItemForCartDto.builder()
                                                .id(cartItem.getId())
                                                .name(cartItem.getItem().getName())
                                                .photoUrlList(cartItem.getItem().getPhotoUrlList().stream().toList())
                                                .count(cartItem.getCount())
                                                .size(cartItem.getSize().getValue())
                                                .color(cartItem.getColor().getValue())
                                                .finalPrice(cartItem.getPrice())
                                                .build()
                                ).toList()
                        )
                        .fullName(order.getFullName())
                        .build()
        ).toList();
    }
}
