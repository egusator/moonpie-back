package com.example.moonpie_back.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Getter
public enum ItemCategory {
    BLOUSE("Блуза"),
    KIMONO("Кимоно"),
    TUNIC("Туника"),
    TROUSERS("Брюки"),
    JOGGERS("Джоггеры"),
    OVERALL("Комбинезон");

    private final String value;

    public static ItemCategory of(String value) {
        for (ItemCategory category : values()) {
            if (category.value.equals(value)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown item category: " + value);
    }
}
