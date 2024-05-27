package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ItemCategory;
import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.core.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Каталог")
@Validated
@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final ItemService itemService;

    @GetMapping(ApiPaths.ITEM)
    public List<ItemForCatalogDto> getAllItems(@RequestParam(required = false) String itemCategory)  {
        return itemService.getItemsForCatalog(itemCategory);
    }
}
