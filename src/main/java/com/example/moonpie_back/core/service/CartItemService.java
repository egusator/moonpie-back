package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.AddCartItemDto;
import com.example.moonpie_back.api.dto.ItemForCartDto;
import com.example.moonpie_back.core.entity.*;

import com.example.moonpie_back.core.repository.ClientRepository;
import com.example.moonpie_back.core.repository.ItemRepository;
import com.example.moonpie_back.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final ItemRepository itemRepository;

    public List<ItemForCartDto> getCartItemsByClientId(Long clientId) {
        Client client = clientRepository.findClientById(clientId);
        Set<Order> orders = client.getOrders();
        List<Order> createdOrders = orders.stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.CREATED)
                .toList();
        if (createdOrders.isEmpty())
            return Collections.EMPTY_LIST;
        Order createdOrder = createdOrders.get(0);
        return createdOrder
                .getCartItems()
                .stream().map(cartItem -> {
                    return ItemForCartDto.builder()
                            .id(cartItem.getId())
                            .name(cartItem.getItem().getName())
                            .count(cartItem.getCount().toString())
                            .finalPrice(cartItem.getPrice().toString())
                            .photoUrl(cartItem.getItem().getPhotoUrl())
                            .color(cartItem.getColor().getValue())
                            .size(cartItem.getSize().getValue()).build();
                }).toList();
    }

    public void addItemToCart(Long clientId, AddCartItemDto addCartItemDto) {
        Client client = clientRepository.findClientById(clientId);

        Item item = itemRepository.findAllByName(addCartItemDto.itemName()).get(0);

        Color itemColor = item.getColors()
                .stream()
                .filter(color -> color.getValue().equals(addCartItemDto.color()))
                .toList().get(0);
        CartItem cartItem = CartItem.builder()
                .item(item)
                .price(item.getPrice().multiply(BigDecimal.valueOf(addCartItemDto.quantity())))
                .count(addCartItemDto.quantity())
                .size(item.getSizes()
                        .stream()
                        .filter(size -> size.getValue().equals(addCartItemDto.size()))
                        .toList().get(0))
                .color(item.getColors()
                        .stream()
                        .filter(color -> color.getValue().equals(addCartItemDto.color()))
                        .toList().get(0)).build();

        Set<Order> orders = client.getOrders().stream().
                filter(order -> order.getOrderStatus() == OrderStatus.CREATED)
                .collect(Collectors.toSet());

        Order currentOrder;
        if (orders.isEmpty() || orders == null) {
            currentOrder = Order.builder()
                    .orderStatus(OrderStatus.CREATED)
                    .client(client)
                    .cartItems(Arrays.asList(cartItem))
                    .build();
            orderRepository.save(currentOrder);
        } else {
            currentOrder = orders.stream().findFirst().get();
            currentOrder.getCartItems().add(cartItem);
            orderRepository.save(currentOrder);
        }

    }
}
