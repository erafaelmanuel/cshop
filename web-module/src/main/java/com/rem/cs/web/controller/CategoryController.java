package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.category.CategoryService;
import com.rem.cs.data.jpa.entity.Item;
import com.rem.cs.data.jpa.item.ItemService;
import com.rem.cs.web.dto.CategoryDto;
import com.rem.cs.web.dto.ItemDto;
import com.rem.cs.web.util.PageHelper;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public List<CategoryDto> setUpCategories() {
        final Mapper mapper = new Mapper();

        return mapper.from(categoryService.findByParenIsNull()).toListOf(CategoryDto.class);
    }

    @PostMapping("/category/subCategoriesOf")
    public String getSubCategories(@RequestParam("cid") String categoryId, Model model) {
        final Mapper mapper = new Mapper();

        model.addAttribute("categories", mapper
                .from(categoryService.findByParentId(categoryId))
                .toArrayOf(CategoryDto.class));
        return "fragment/nav/category";
    }

    @GetMapping("{categoryId}.html")
    public String getItemsByCategory(@PathVariable("categoryId") String categoryId,
                                     Pageable pageable, Model model) {
        final Mapper mapper = new Mapper();
        final List<ItemDto> items = new ArrayList<>();
        final int currentPage = pageable.getPageNumber() + 1;
        final List<String> categoryIds = new ArrayList<>();
        final Page<Item> pageItems;

        categoryIds.add(categoryId);
        categoryIds.addAll(mapper
                .in(categoryService.findSubCategories(categoryId))
                .just("id")
                .toListOf(String.class));
        pageItems = itemService.findByCategoryIds(categoryIds, pageable);
        pageItems.forEach(item -> items.add(mapper.from(item).toInstanceOf(ItemDto.class)));

        final PageHelper helper = new PageHelper(currentPage, pageItems);

        model.addAttribute("items", items);
        model.addAttribute("pages", helper.getPages());
        model.addAttribute("pageNext", helper.getPageNext());
        model.addAttribute("pagePrev", helper.getPagePrev());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("prevEllipsis", helper.hasPrevEllipsis());
        model.addAttribute("nextEllipsis", helper.hasNextEllipsis());
        model.addAttribute("isFirst", pageItems.isFirst());
        model.addAttribute("isLast", pageItems.isLast());
        return "catalog";
    }
}
