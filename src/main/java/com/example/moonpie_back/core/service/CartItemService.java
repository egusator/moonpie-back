package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.AddCartItemDto;
import com.example.moonpie_back.api.dto.ItemForCartDto;
import com.example.moonpie_back.core.entity.*;
import com.example.moonpie_back.core.repository.CartItemRepository;

import com.example.moonpie_back.core.repository.ClientRepository;
import com.example.moonpie_back.core.repository.ItemRepository;
import com.example.moonpie_back.core.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.pulsar.PulsarProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final ItemRepository itemRepository;

    public List<ItemForCartDto> getCartItemsByClientId(Long clientId) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.CREATED)
                .toList()
                .get(0)
                .getCartItems()
                .stream().map(cartItem -> {
                    return ItemForCartDto.builder()
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

        CartItem cartItem = CartItem.builder()
                .item(item)
                .price(item.getPrice().multiply(BigDecimal.valueOf(addCartItemDto.quantity())))
                .size(item.getSizes()
                        .stream()
                        .filter(size -> size.getValue() == addCartItemDto.size())
                        .toList().get(0))
                .color(item.getColors()
                        .stream()
                        .filter(color -> color.getValue() == addCartItemDto.color())
                        .toList().get(0)).build();

        Set<Order> orders = client.getOrders();

        Order currentOrder;
        if (orders == null) {
            currentOrder = Order.builder()
                    .orderStatus(OrderStatus.CREATED)
                    .client(client)
                    .cartItems(Arrays.asList(cartItem))
                    .build();
        } else {
            currentOrder = orders.stream().
                    filter(order -> order.getOrderStatus() == OrderStatus.CREATED)
                    .toList()
                    .get(0);
        }

        orderRepository.save(currentOrder);
    }
}
