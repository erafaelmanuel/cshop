package io.ermdev.cshop.data.helper;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.service.CategoryService;
import io.ermdev.cshop.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
