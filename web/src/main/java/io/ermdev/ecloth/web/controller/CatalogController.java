package io.ermdev.ecloth.web.controller;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.helper.CategoryHelper;
import io.ermdev.ecloth.data.helper.ItemHelper;
import io.ermdev.ecloth.data.service.CategoryService;
import io.ermdev.ecloth.data.service.ItemService;
import io.ermdev.ecloth.model.entity.Category;
import io.ermdev.ecloth.model.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"cartItems"})
public class CatalogController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    private final CategoryHelper categoryHelper;
    private final ItemHelper itemHelper;

    @Autowired
    public CatalogController(ItemService itemService, CategoryService categoryService, ItemHelper itemHelper,
                             CategoryHelper categoryHelper) {
        this.itemService = itemService;
        this.categoryService = categoryService;
        this.itemHelper = itemHelper;
        this.categoryHelper = categoryHelper;
    }

    @GetMapping("catalog")
    public String load(ModelMap modelMap,
                       @RequestParam(required = false, value = "q") String query,
                       @RequestParam(required = false, value = "c") Long categoryId,
                       @RequestParam(required = false, value = "p") Long parentId) {
        try {
            final List<Item> items = new ArrayList<>();
            final List<Category> categories = new ArrayList<>();

            if(query != null)
                items.addAll(itemService.findByName(query));
            else if(categoryId != null)
                items.addAll(itemHelper.searchFromCategories(categoryHelper.startWith(categoryId)));
            else
                items.addAll(itemService.findAll());

            if(parentId != null) {
                categories.addAll(categoryService.findByParent(parentId));
            } else {
                categories.add(categoryService.findById(1L));
            }

            modelMap.addAttribute("items", items);
            modelMap.addAttribute("categories", categories);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        return "catalog";
    }


}
