package com.rem.cs.data.jpa.category;

import com.rem.cs.data.jpa.entity.Category;
import com.rem.cs.data.jpa.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Deprecated
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findByParentId(String parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    public List<Category> findByParenIsNull() {
        return categoryRepository.findByParentIsNull();
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
