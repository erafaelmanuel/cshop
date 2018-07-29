package com.rem.cs.web.controller;

import com.rem.cs.rest.client.resource.client.Category;
import com.rem.cs.rest.client.resource.client.CategoryService;
import com.rem.cs.rest.client.resource.item.Item;
import com.rem.cs.rest.client.resource.item.ItemService;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CategoryController {

    final private CategoryService categoryService;
    final private ItemService itemService;

    @Autowired
    public CategoryController(CategoryService categoryService, ItemService itemService) {
        this.categoryService = categoryService;
        this.itemService = itemService;
    }

    @ModelAttribute(name = "categories")
    public List<Category> setUpCategories() {
        return new ArrayList<>(categoryService.findByParentIsNull(0, 0, null).getContent());
    }

    @Deprecated
    @GetMapping("/{categoryId}.html")
    public String getItemsByCategory(@PathVariable("categoryId") String categoryId,
                                     @RequestParam(name = "page", required = false) Integer page,
                                     @RequestParam(name = "size", required = false) Integer size,
                                     @RequestParam(name = "sort", required = false) String sort, Model model) {

        final List<String> categoryIds = new ArrayList<>();
        final PagedResources<Item> resources;

        categoryIds.add(categoryId);
        categoryIds.addAll(new Mapper()
                .in(categoryService.findByAncestor(categoryId, 1, 20, null).getContent())
                .just("id")
                .toListOf(String.class));
        resources = itemService.findByCategoryId(categoryIds, 1, 20, null);

        model.addAttribute("items", resources.getContent());
        return "catalog";
    }
}
