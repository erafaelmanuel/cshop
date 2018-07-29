package com.rem.cs.data.jpa.service;

import com.rem.cs.data.jpa.entity.Category;
import com.rem.cs.data.jpa.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryJpaService")
public class CategoryService {

    private CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<Category> findByAncestor(String id) {
        final List<Category> categories = new ArrayList<>();
        categoryRepo.findByParentId(id, null).forEach(category -> {
            categories.add(category);
            categories.addAll(findByAncestor(category.getId()));
        });
        return categories;
    }
}
