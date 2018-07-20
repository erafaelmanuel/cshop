package com.rem.cs.data.jpa.category;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findByParentId(String parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    public List<Category> findSubCategories(String id) {
        final List<Category> categories = new ArrayList<>();
        findByParentId(id).forEach( category -> {
            categories.add(category);
            categories.addAll(findSubCategories(category.getId()));
        });
        return categories;
    }
}
