package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ItemFullInfoDto;
import com.example.moonpie_back.core.entity.Item;
import com.example.moonpie_back.core.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CRUD предметов")
@Validated
@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping(ApiPaths.ITEM_BY_NAME)
    public ItemFullInfoDto getItemByName(@RequestParam String name) {
        return itemService.getItemByName(name);
    }
}
