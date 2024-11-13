package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.core.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Получение предметов из каталога с пагинацией и фильтру по категории")
    public List<ItemForCatalogDto> getAllItems(
            @RequestParam(required = false) String itemCategory,
            @RequestParam(required = false, defaultValue = ItemService.DEFAULT_ITEM_PAGE_SIZE) int limit,
            @RequestParam(required = false, defaultValue = "0") int offset
            ) {
        return itemService.getItemsForCatalog(itemCategory, limit, offset);
    }
}
