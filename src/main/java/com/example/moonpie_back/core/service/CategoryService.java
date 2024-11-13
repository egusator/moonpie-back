package com.example.moonpie_back.core.service;

import com.example.moonpie_back.api.dto.CategoriesTree;
import com.example.moonpie_back.api.dto.CategoryDto;
import com.example.moonpie_back.api.dto.CreateCategoryDto;
import com.example.moonpie_back.api.dto.UpdateCategoryDto;
import com.example.moonpie_back.core.entity.Category;
import com.example.moonpie_back.core.event.UserAuthEvent;
import com.example.moonpie_back.core.exception.BusinessException;
import com.example.moonpie_back.core.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getUpperLevelCategories() {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getParent() == null)
                .map(
                        category -> CategoryDto.builder()
                                .name(category.getName())
                                .build()
                ).toList();
    }

    public List<CategoryDto> getSubcategories(String categoryName) {
        Optional<Category> categoryByName = categoryRepository.findCategoryByName(categoryName);

        if (categoryByName.isEmpty()) {
            throw new BusinessException(
                    UserAuthEvent.CATEGORY_WITH_THIS_NAME_IS_NOT_FOUND,
                    "Category with this name is not found"
            );
        }

        return categoryByName.get().getChildren().stream()
                .map(
                        category -> CategoryDto.builder()
                                .name(category.getName())
                                .build()
                )
                .toList();
    }

    public void addCategory(CreateCategoryDto createCategoryDto) {

        if (createCategoryDto.parentCategoryName().isEmpty()) {

            Category category = Category.builder()
                    .name(createCategoryDto.categoryName())
                    .build();

            categoryRepository.save(category);

        } else {

            Optional<Category> parentCategoryByName =
                    categoryRepository.findCategoryByName(
                            createCategoryDto.parentCategoryName()
                    );

            if (parentCategoryByName.isEmpty()) {
                throw new BusinessException(
                        UserAuthEvent.CATEGORY_WITH_THIS_NAME_IS_NOT_FOUND,
                        "Category with this name is not found"
                );
            }
            Category category = Category.builder()
                    .name(createCategoryDto.categoryName())
                    .parent(parentCategoryByName.get())
                    .build();

            categoryRepository.save(category);
        }

    }

    public void changeCategoryName(UpdateCategoryDto updateCategoryDto) {
        Optional<Category> categoryByName =
                categoryRepository.findCategoryByName(
                        updateCategoryDto.currentCategoryName()
                );

        if (categoryByName.isEmpty()) {
            throw new BusinessException(
                    UserAuthEvent.CATEGORY_WITH_THIS_NAME_IS_NOT_FOUND,
                    "Category with this name is not found"
            );
        }

        Category category = categoryByName.get();
        category.setName(updateCategoryDto.currentCategoryName());
        categoryRepository.save(category);

    }

    public void deleteCategory(String categoryName) {
        Optional<Category> categoryByName =
                categoryRepository.findCategoryByName(
                        categoryName
                );

        if (categoryByName.isEmpty()) {
            throw new BusinessException(
                    UserAuthEvent.CATEGORY_WITH_THIS_NAME_IS_NOT_FOUND,
                    "Category with this name is not found"
            );
        }

        Category category = categoryByName.get();

        Category parentCategory = category.getParent();

        parentCategory.getChildren().addAll(category.getChildren());

        parentCategory.getChildren().remove(category);

        categoryRepository.delete(category);

        categoryRepository.save(parentCategory);

    }

    public CategoriesTree getAllCategories() {
        List<Category> allCategories = categoryRepository.findAll();
        return null;

    }

}
