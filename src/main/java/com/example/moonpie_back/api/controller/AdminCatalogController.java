package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.CreateItemDto;
import com.example.moonpie_back.api.dto.UpdateItemDto;
import com.example.moonpie_back.core.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Каталог (админ)")
@Validated
@RestController
@RequiredArgsConstructor
public class AdminCatalogController {

    private final ItemService itemService;


    @PostMapping(ApiPaths.ADMIN_ITEM)
    @Operation(summary = "Создание нового товара")
    public void createItem(@RequestBody CreateItemDto createItemDto) {
        itemService.createItem(createItemDto);
    }

    @PatchMapping(ApiPaths.ADMIN_ITEM)
    @Operation(summary = "Обновление товара")
    public void updateItem(@PathVariable Long id, @RequestBody UpdateItemDto updateItemDto) {
        itemService.updateItem(id, updateItemDto);
    }

    @DeleteMapping(ApiPaths.ADMIN_ITEM)
    @Operation(summary = "Удаление товара")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
