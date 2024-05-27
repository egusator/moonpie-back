package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.ItemCategory;
import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.api.dto.ItemFullInfoDto;
import com.example.moonpie_back.core.entity.Color;
import com.example.moonpie_back.core.entity.Item;
import com.example.moonpie_back.core.entity.Size;
import com.example.moonpie_back.core.repository.ItemRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemForCatalogDto> getItemsForCatalog(String category) {
        return itemRepository.findAll().stream()
                .filter(item -> (category == null || category.equals(item.getCategory())))
                .map(item -> {
                    return ItemForCatalogDto.builder()
                            .name(item.getName())
                            .size(item.getSizes().stream().map(Size::getValue).toList())
                            .color(item.getColors().stream().map(Color::getValue).toList())
                            .price(item.getPrice().toString())
                            .photoUrl(item.getPhotoUrl())
                            .build();
                }).toList();
    }

    public ItemFullInfoDto getItemByName(String name) {
        Item item = itemRepository.findAllByName(name).get(0);
        return ItemFullInfoDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .photoUrl(item.getPhotoUrl())
                .price(item.getPrice().toString())
                .category(item.getCategory())
                .colors(item.getColors().stream().map(Color::getValue).toList())
                .sizes(item.getSizes().stream().map(Size::getValue).toList())
                .build();
    }
}


