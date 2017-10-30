package io.ermdev.ecloth.data.service;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.exception.UnsatisfiedEntityException;
import io.ermdev.ecloth.data.mapper.CategoryRepository;
import io.ermdev.ecloth.model.entity.Category;
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

    public Category findById(Long categoryId) throws EntityNotFoundException {
        final Category category = categoryRepository.findById(categoryId);
        if(category == null)
            throw new EntityNotFoundException("No category found with id " + categoryId);
        return category;
    }

    public List<Category> findByParent(Long parentId) throws EntityNotFoundException {
        List<Category> categories = categoryRepository.findByParent(parentId);
        if(categories == null)
            throw new EntityNotFoundException("No category found");
        return categories;
    }
    public List<Category> findAll() throws EntityNotFoundException {
        List<Category> categories = categoryRepository.findAll();
        if(categories == null)
            throw new EntityNotFoundException("No category found");
        return categories;
    }

    public Category add(Category category) throws EntityNotFoundException, UnsatisfiedEntityException {
        if(category.getName() == null || category.getName().trim().equals(""))
            throw new UnsatisfiedEntityException("Name is required");
        if(category.getDescription() == null || category.getDescription().trim().equals(""))
            throw new UnsatisfiedEntityException("Description is required");
        if(category.getParentId() != null && categoryRepository.findById(category.getParentId()) == null)
            throw new EntityNotFoundException("No category found with id " + category.getParentId());

        categoryRepository.add(category);
        return category;
    }

    public Category updateById(Long categoryId, Category category) throws EntityNotFoundException {
        Category oldCategory = findById(categoryId);
        if(category == null)
            return oldCategory;
        category.setId(categoryId);
        if(category.getName() == null || category.getName().trim().equals(""))
            category.setName(oldCategory.getName());
        if(category.getDescription() == null || category.getDescription().trim().equals(""))
            category.setDescription(oldCategory.getDescription());
        if(category.getParentId() == null || category.getParentId() < 1)
            category.setParentId(oldCategory.getParentId());
        categoryRepository.updateById(category);

        return category;
    }

    public Category deleteById(Long categoryId) throws EntityNotFoundException {
        Category category = categoryRepository.findById(categoryId);
        categoryRepository.deleteById(categoryId);
        return category;
    }
}
