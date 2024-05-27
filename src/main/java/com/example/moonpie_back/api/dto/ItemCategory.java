package com.example.moonpie_back.api.dto;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum ItemCategory {
    PANTS("Pants"),
    OVERALLS("Overalls"),
    BLOUSES("Blouses");
    
    private String value;
}
