package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.IdGenerator;
import io.ermdev.cshop.data.entity.Category;
import io.ermdev.cshop.data.repository.CategoryRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findById(Long categoryId) throws EntityException {
        final Category category = categoryRepository.findById(categoryId);
        if (category != null) {
            return category;
        } else {
            throw new EntityException("No category found");
        }
    }

    public List<Category> findByParentId(Long parentId) throws EntityException {
        List<Category> categories = categoryRepository.findByParentId(parentId);
        if (categories != null) {
            return categories;
        } else {
            throw new EntityException("No category found");
        }
    }

    public List<Category> findAll() throws EntityException {
        List<Category> categories = categoryRepository.findAll();
        if (categories != null) {
            return categories;
        } else {
            throw new EntityException("No category found");
        }
    }

    public Category save(Category category) throws EntityException {
        if (category != null) {
            if (category.getId() == null) {
                if (category.getName() == null || category.getName().trim().isEmpty()) {
                    throw new EntityException("Name is required");
                }
                if (category.getDescription() == null || category.getDescription().trim().isEmpty()) {
                    throw new EntityException("Description is required");
                }
                final long generatedId = IdGenerator.randomUUID();
                category.setId(generatedId);
                categoryRepository.add(category);
                return category;
            } else {
                Category o = categoryRepository.findById(category.getId());
                if (o != null) {
                    if (category.getName() == null || category.getName().trim().isEmpty()) {
                        category.setName(o.getName());
                    }
                    if (category.getDescription() == null || category.getDescription().trim().isEmpty()) {
                        category.setDescription(o.getDescription());
                    }
                    categoryRepository.update(category);
                    return category;
                } else {
                    category.setId(null);
                    return save(category);
                }
            }
        } else {
            throw new NullPointerException("Category is null");
        }
    }

    public Category delete(Long categoryId) throws EntityException {
        final Category category = categoryRepository.findById(categoryId);
        if (category != null) {
            categoryRepository.delete(category);
            return category;
        } else {
            throw new EntityException("No category found");
        }
    }

    public Category delete(Category category) throws EntityException {
        if (category != null) {
            final Category o = categoryRepository.findById(category.getId());
            if (o != null) {
                categoryRepository.delete(o);
                return o;
            } else {
                throw new EntityException("No category found");
            }
        } else {
            throw new NullPointerException("Category is null");
        }
    }
}
