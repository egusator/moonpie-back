package com.example.moonpie_back.api.controller;

import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.CategoriesTree;
import com.example.moonpie_back.api.dto.CategoryDto;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Категории")
@Validated
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Получение категорий верхнего уровня")
    @GetMapping(ApiPaths.CATEGORY_UPPER)
    public List<CategoryDto> getUpperLevelCategories() {
        return categoryService.getUpperLevelCategories();
    }

    @Operation(summary = "Получение всего дерева категорий", hidden = true)
    @GetMapping(ApiPaths.CATEGORY)
    public CategoriesTree getAllCategories() {
        //TODO
        throw new BusinessException(UserAuthEvent.NOT_IMPLEMENTED, "Method not implemented yet");
    }

    @Operation(summary = "Получение подкатегории указанной категории")
    @GetMapping(ApiPaths.SUBCATEGORY)
    public List<CategoryDto> getSubcategories(@RequestParam("name") String categoryName) {
        return categoryService.getSubcategories(categoryName);
    }



}
