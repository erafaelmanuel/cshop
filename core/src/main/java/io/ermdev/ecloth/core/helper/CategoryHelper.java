package io.ermdev.ecloth.core.helper;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.CategoryService;
import io.ermdev.ecloth.model.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryHelper {

    private CategoryService categoryService;

    public CategoryHelper(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<Category> startWith(Long categoryId) throws EntityNotFoundException {
        final List<Category> categories = new ArrayList<>();
        categories.add(categoryService.findById(categoryId));
        for(Category category : categoryService.findByParent(categoryId)) {
            categories.addAll(startWith(category.getId()));
        }
        return categories;
    }
}
