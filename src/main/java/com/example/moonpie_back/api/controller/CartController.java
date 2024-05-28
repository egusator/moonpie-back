package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ItemForCartDto;
import com.example.moonpie_back.core.service.CartItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Корзина")
@Validated
@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @PostMapping(ApiPaths.CART)
    public void addItemInCart() {

    }

    @GetMapping(ApiPaths.CART)
    public List<ItemForCartDto> getCartOfUser() {
        return null;
    }
}