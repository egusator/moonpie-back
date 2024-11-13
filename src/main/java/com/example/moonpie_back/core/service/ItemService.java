package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.CreateItemDto;
import com.example.moonpie_back.api.dto.ItemForCatalogDto;
import com.example.moonpie_back.api.dto.ItemFullInfoDto;
import com.example.moonpie_back.api.dto.UpdateItemDto;
import com.example.moonpie_back.core.entity.Category;
import com.example.moonpie_back.core.entity.Color;
import com.example.moonpie_back.core.entity.Item;
import com.example.moonpie_back.core.entity.Size;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.repository.CategoryRepository;
import com.example.moonpie_back.core.repository.ColorRepository;
import com.example.moonpie_back.core.repository.ItemRepository;
import com.example.moonpie_back.core.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    private final CategoryRepository categoryRepository;

    private final SizeRepository sizeRepository;

    private final ColorRepository colorRepository;

    public static final String DEFAULT_ITEM_PAGE_SIZE = "10";


    @Transactional
    public List<ItemForCatalogDto> getItemsForCatalog(
            String categoryName,
            int limit,
            int offset
    ) {
        List<ItemForCatalogDto> result;

        if (categoryName == null) {
            result = itemRepository.findItemsLimitOffset(limit, offset).stream()
                    .map(item -> {
                        return ItemForCatalogDto.builder()
                                .name(item.getName())
                                .size(item.getSizes().stream().map(Size::getValue).toList())
                                .color(item.getColors().stream().map(Color::getValue).toList())
                                .price(item.getPrice().toString())
                                .build();
                    }).toList();
        } else {
            result = itemRepository.findItemsByCategoryName(categoryName, limit, offset).stream()
                    .map(item -> {
                        return ItemForCatalogDto.builder()
                                .name(item.getName())
                                .size(item.getSizes().stream().map(Size::getValue).toList())
                                .color(item.getColors().stream().map(Color::getValue).toList())
                                .price(item.getPrice().toString())
                                .build();
                    }).toList();
        }
        return result;
    }

    public ItemFullInfoDto getItemByName(String name) {
        Item item = itemRepository.findByName(name);
        return ItemFullInfoDto.builder()
                .name(item.getName())
                .description(item.getDescription())
                .price(item.getPrice().toString())
                .category(item.getCategory().getName())
                .colors(item.getColors().stream().map(Color::getValue).toList())
                .sizes(item.getSizes().stream().map(Size::getValue).toList())
                .build();
    }

    public void createItem(CreateItemDto createItemDto) {
        Optional<Category> categoryByName = categoryRepository.findCategoryByName(createItemDto.category());

        if (categoryByName.isEmpty()) {
            throw new BusinessException(
                    UserAuthEvent.CATEGORY_WITH_THIS_NAME_IS_NOT_FOUND,
                    "Category with this name is not found"
            );
        }

        Set<Color> colors = createItemDto.colors().stream()
                .map(
                        color -> colorRepository.findColorByValue(color)
                                .orElseThrow(() -> new BusinessException(
                                                UserAuthEvent.COLOR_WITH_THIS_VALUE_IS_NOT_FOUND,
                                                "Color with this value is not found"
                                        )
                                )
                ).collect(Collectors.toSet());

        Set<Size> sizes = createItemDto.sizes().stream()
                .map(
                        size -> sizeRepository.findSizeByValue(size)
                                .orElseThrow(() -> new BusinessException(
                                                UserAuthEvent.SIZE_WITH_THIS_VALUE_IS_NOT_FOUND,
                                                "Size with this value is not found"
                                        )
                                )
                ).collect(Collectors.toSet());

        Item item = Item.builder()
                .name(createItemDto.name())
                .price(createItemDto.price())
                .colors(colors)
                .sizes(sizes)
                .description(createItemDto.description())
                .category(categoryByName.get())
                .build();

        itemRepository.save(item);
    }

    public void updateItem(Long itemId, UpdateItemDto updateItemDto) {

        Optional<Item> optionalItem = itemRepository.findById(itemId);

        Optional<Category> categoryByName;

        if (updateItemDto.category() != null) {
            categoryByName = categoryRepository.findCategoryByName(updateItemDto.category());

            if (categoryByName.isEmpty()) {
                throw new BusinessException(
                        UserAuthEvent.CATEGORY_WITH_THIS_NAME_IS_NOT_FOUND,
                        "Category with this name is not found"
                );
            }
        } else {
            categoryByName = Optional.empty();
        }

        Set<Color> colors;
        Set<Size> sizes;

        if(updateItemDto.colors() != null) {
            colors = updateItemDto.colors().stream()
                    .map(
                            color -> colorRepository.findColorByValue(color)
                                    .orElseThrow(() -> new BusinessException(
                                                    UserAuthEvent.COLOR_WITH_THIS_VALUE_IS_NOT_FOUND,
                                                    "Color with this value is not found"
                                            )
                                    )
                    ).collect(Collectors.toSet());
        } else {
            colors = Collections.emptySet();
        }

        if(updateItemDto.colors() != null) {
            sizes = updateItemDto.sizes().stream()
                    .map(
                            sizeValue -> sizeRepository.findSizeByValue(sizeValue)
                                    .orElseThrow(() -> new BusinessException(
                                                    UserAuthEvent.SIZE_WITH_THIS_VALUE_IS_NOT_FOUND,
                                                    "Size with this value is not found"
                                            )
                                    )
                    ).collect(Collectors.toSet());
        } else {
            sizes = Collections.emptySet();
        }

        optionalItem.ifPresentOrElse(
                item -> {
                    item.setCategory(
                            updateItemDto.category() != null ?
                                    categoryByName.get() :
                                    item.getCategory()
                    );

                    item.setSizes(
                            sizes.isEmpty() ?
                                    item.getSizes() :
                                    sizes
                    );

                    item.setColors(
                            colors.isEmpty() ?
                                    item.getColors() :
                                    colors
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


