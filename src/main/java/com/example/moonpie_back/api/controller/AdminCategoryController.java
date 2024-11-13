package com.example.moonpie_back.api.controller;


import com.example.moonpie_back.api.ApiPaths;
import com.example.moonpie_back.api.dto.CreateCategoryDto;
import com.example.moonpie_back.api.dto.UpdateCategoryDto;
import com.example.moonpie_back.core.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Категории (админ)")
@Validated
@RestController
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;


    @Operation(summary = "Добавление категории")
    @PostMapping(ApiPaths.ADMIN_CATEGORY)
    public void addCategory(@RequestBody CreateCategoryDto createCategoryDto) {
        categoryService.addCategory(createCategoryDto);
    }


    @Operation(summary = "Изменение названия категории")
    @PatchMapping(ApiPaths.ADMIN_CATEGORY)
    public void changeCategoryName(@RequestBody UpdateCategoryDto updateCategoryDto) {
        categoryService.changeCategoryName(updateCategoryDto);
    }

    @Operation(summary = "Удаление категории")
    @DeleteMapping(ApiPaths.ADMIN_CATEGORY)
    public void deleteCategory(@RequestParam("name") String categoryName) {
        categoryService.deleteCategory(categoryName);
    }

}
