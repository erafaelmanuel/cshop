package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.category.CategoryService;
import com.rem.cs.web.dto.CategoryDto;
import com.rem.mappyfy.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category/subCategoriesOf")
    public String getSubCategories(@RequestParam("cid") String categoryId, Model model) {
        final Mapper mapper = new Mapper();

        model.addAttribute("categories", mapper
                .from(categoryService.findSubCategories(categoryId))
                .ignore("parent")
                .ignore("items")
                .toArrayOf(CategoryDto.class));
        return "fragment/nav/category";
    }
}
