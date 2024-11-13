package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.AddCartItemDto;
import com.example.moonpie_back.api.dto.CustomSize;
import com.example.moonpie_back.api.dto.ItemForCartDto;
import com.example.moonpie_back.core.entity.*;

import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
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
        Optional<Client> clientById = clientRepository.findClientById(clientId);

        if (clientById.isEmpty()) {
            throw new BusinessException(UserAuthEvent.USER_WITH_THIS_ID_IS_NOT_FOUND,
                    "User with this id does not exist");
        }

        Client client = clientById.get();

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
                            .count(cartItem.getCount())
                            .finalPrice(cartItem.getPrice())
                            .color(cartItem.getColor().getValue())
                            .size(cartItem.getSize() != null ? cartItem.getSize().getValue() : null)
                            .customSize(cartItem.getSize() == null ? new CustomSize(cartItem.getHip(), cartItem.getWaist(), cartItem.getChest()) : null)
                            .build();
                }).toList();
    }

    public void addItemToCart(Long clientId, AddCartItemDto addCartItemDto) {
        Client client = clientRepository.findClientById(clientId).get();

        Item item = itemRepository.findByName(addCartItemDto.itemName());

        Color itemColor = item.getColors()
                .stream()
                .filter(color -> color.getValue().equals(addCartItemDto.color()))
                .toList().getFirst();

        CartItem cartItem = CartItem.builder()
                .item(item)
                .price(item.getPrice().multiply(BigDecimal.valueOf(addCartItemDto.quantity())))
                .count(addCartItemDto.quantity())
                .color(itemColor).build();

        if (addCartItemDto.size() == null) {
            cartItem.setWaist(addCartItemDto.customSize().waist());
            cartItem.setHip(addCartItemDto.customSize().hip());
            cartItem.setChest(addCartItemDto.customSize().chest());
        } else {
            cartItem.setSize(item.getSizes()
                    .stream()
                    .filter(color -> color.getValue().equals(addCartItemDto.size()))
                    .toList().getFirst());
        }

        Set<Order> orders = client.getOrders().stream().
                filter(order -> order.getOrderStatus() == OrderStatus.CREATED)
                .collect(Collectors.toSet());

        Order currentOrder;
        if (orders.isEmpty()) {
            currentOrder = Order.builder()
                    .orderStatus(OrderStatus.CREATED)
                    .client(client)
                    .cartItems(List.of(cartItem))
                    .build();
            orderRepository.save(currentOrder);
        } else {
            currentOrder = orders.stream().findFirst().get();
            currentOrder.getCartItems().add(cartItem);
            orderRepository.save(currentOrder);
        }
    }
}
