package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.core.service.SavedItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Избранное")
@Validated
@RestController
@RequiredArgsConstructor
public class SavedItemController {

    private final SavedItemService savedItemService;

    @GetMapping(ApiPaths.SAVED)
    @Operation(summary = "Получение избранного")
    public List<ItemForCatalogDto> getSavedItems() {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return savedItemService.getSavedItems(clientId);
    }

    @PostMapping(ApiPaths.SAVED)
    @Operation(summary = "Добавление в избранное")
    public void addToSavedItems(@RequestParam String itemName) {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        savedItemService.saveItem(clientId, itemName);
    }

    @DeleteMapping(ApiPaths.SAVED)
    @Operation(summary = "Убрать из избранного")
    public void deleteFromSavedItems(@RequestParam String itemName) {
        Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        savedItemService.deleteFromSavedItems(clientId, itemName);
    }
}
