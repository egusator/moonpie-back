package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.AddCartItemDto;
import com.example.moonpie_back.api.dto.ItemForCartDto;
import com.example.moonpie_back.core.service.CartItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Корзина")
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping(ApiPaths.CART)
    public void addItemInCart(AddCartItemDto addCartItemDto) {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        cartItemService.addItemToCart(1L, addCartItemDto);
    }

    @GetMapping(ApiPaths.CART)
    public List<ItemForCartDto> getCartOfUser() {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return cartItemService.getCartItemsByClientId(clientId);
    }
}
