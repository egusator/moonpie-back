package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.CreateItemDto;
import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.api.dto.UpdateItemDto;
import com.example.moonpie_back.core.enums.ItemCategory;
import com.example.moonpie_back.core.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Каталог")
@Validated
@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final ItemService itemService;

    @GetMapping(ApiPaths.ITEM)
    public List<ItemForCatalogDto> getAllItems(@RequestParam(required = false) ItemCategory itemCategory)  {
        return itemService.getItemsForCatalog(itemCategory);
    }


    @PostMapping(ApiPaths.ITEM)
    public void createItem(@RequestBody CreateItemDto createItemDto) {
        itemService.createItem(createItemDto);
    }

    @PatchMapping(ApiPaths.ITEM)
    public void updateItem(@PathVariable Long id, @RequestBody UpdateItemDto updateItemDto) {
        itemService.updateItem(id, updateItemDto);
    }

    @DeleteMapping(ApiPaths.ITEM)
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
