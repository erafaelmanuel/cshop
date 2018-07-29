package com.rem.cs.web.controller;

import com.rem.cs.rest.client.resource.client.Category;
import com.rem.cs.rest.client.resource.client.CategoryService;
import com.rem.cs.rest.client.resource.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Category> setUpCategories() {
        return new ArrayList<>(categoryService.findByParentIsNull(0, 0, null).getContent());
    }

    @PostMapping("/category/subCategoriesOf")
    public String getSubCategories(@RequestParam("cid") String categoryId, Model model) {
        model.addAttribute("categories", categoryService.findByParentId(categoryId, 0, 0, null)
                .getContent());
        return "fragment/nav/category";
    }

    @Deprecated
    @GetMapping("{categoryId}.html")
    public String getItemsByCategory(@PathVariable("categoryId") String categoryId,
                                     Pageable pageable, Model model) {
//        final Mapper mapper = new Mapper();
//        final List<ItemDto> items = new ArrayList<>();
//        final int currentPage = pageable.getPageNumber() + 1;
//        final List<String> categoryIds = new ArrayList<>();
//        final Page<Item> pageItems;
//
//        categoryIds.add(categoryId);
//        categoryIds.addAll(mapper
//                .in(categoryService.findByAncestor(categoryId, pageable.getPageNumber(), pageable.getPageSize(),
//                        null).getContent())
//                .just("id")
//                .toListOf(String.class));
//        pageItems = itemService.findByCategoryIds(categoryIds, pageable);
//        pageItems.forEach(item -> items.add(mapper.from(item).toInstanceOf(ItemDto.class)));
//
//        final PageHelper helper = new PageHelper(currentPage, pageItems);
//
//        model.addAttribute("items", items);
//        model.addAttribute("pages", helper.getPages());
//        model.addAttribute("pageNext", helper.getPageNext());
//        model.addAttribute("pagePrev", helper.getPagePrev());
//        model.addAttribute("currentPage", currentPage);
//        model.addAttribute("prevEllipsis", helper.hasPrevEllipsis());
//        model.addAttribute("nextEllipsis", helper.hasNextEllipsis());
//        model.addAttribute("isFirst", pageItems.isFirst());
//        model.addAttribute("isLast", pageItems.isLast());
        return "catalog";
    }
}
