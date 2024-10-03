package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.CreateItemDto;
import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.api.dto.ItemFullInfoDto;
import com.example.moonpie_back.api.dto.UpdateItemDto;
import com.example.moonpie_back.core.ItemCategory;
import com.example.moonpie_back.core.entity.Color;
import com.example.moonpie_back.core.entity.Item;
import com.example.moonpie_back.core.entity.Size;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public List<ItemForCatalogDto> getItemsForCatalog(ItemCategory category) {
        return itemRepository.findAll().stream()
                .filter(item -> (category == null || category.equals(item.getCategory())))
                .map(item -> {
                    return ItemForCatalogDto.builder()
                            .name(item.getName())
                            .size(item.getSizes().stream().map(Size::getValue).toList())
                            .color(item.getColors().stream().map(Color::getValue).toList())
                            .price(item.getPrice().toString())
                            .build();
                }).toList();
    }

    public ItemFullInfoDto getItemByName(String name) {
        Item item = itemRepository.findByName(name);
        return ItemFullInfoDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice().toString())
                .category(item.getCategory().getValue())
                .colors(item.getColors().stream().map(Color::getValue).toList())
                .sizes(item.getSizes().stream().map(Size::getValue).toList())
                .build();
    }

    public void createItem(CreateItemDto createItemDto) {

        Item item = Item.builder()
                .name(createItemDto.name())
                .price(createItemDto.price())
                .colors(createItemDto.colors())
                .sizes(createItemDto.sizes())
                .description(createItemDto.description())
                .category(ItemCategory.of(createItemDto.category()))
                .build();

        itemRepository.save(item);
    }

    public void updateItem(Long itemId, UpdateItemDto updateItemDto) {

        Optional<Item> optionalItem = itemRepository.findById(itemId);

        optionalItem.ifPresentOrElse(
                item -> {
                    item.setCategory(
                            updateItemDto.category() != null ?
                                    ItemCategory.of(updateItemDto.category()) :
                                    item.getCategory()
                    );

                    item.setSizes(
                            updateItemDto.sizes() != null ?
                                    updateItemDto.sizes() :
                                    item.getSizes()
                    );

                    item.setColors(
                            updateItemDto.colors() != null ?
                                    updateItemDto.colors() :
                                    item.getColors()
                    );

                    item.setName(
                            updateItemDto.name() != null ?
                                    updateItemDto.name() :
                                    item.getName()
                    );

                    item.setPrice(
                            updateItemDto.price() != null ?
                                    updateItemDto.price() :
                                    item.getPrice()
                    );

                    item.setDescription(
                            updateItemDto.description() != null ?
                                    updateItemDto.description() :
                                    item.getDescription()
                    );

                },
                () -> {
                    throw new BusinessException(
                            UserAuthEvent.ORDER_WITH_THIS_ID_IS_NOT_FOUND,
                            "Item with this ID is not found!"
                    );
                }
        );
    }

    public void deleteItem(Long itemId) {
        Optional<Item> optionalItem = itemRepository.findById(itemId);

        optionalItem.ifPresentOrElse(
                itemRepository::delete,
                () -> {
                    throw new BusinessException(
                            UserAuthEvent.ITEM_WITH_THIS_ID_IS_NOT_FOUND,
                            "Item with this ID is not found!"
                    );
                }
        );
    }
}


