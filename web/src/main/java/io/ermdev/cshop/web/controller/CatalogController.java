package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.helper.CategoryHelper;
import io.ermdev.cshop.data.helper.ItemHelper;
import io.ermdev.cshop.data.service.CategoryService;
import io.ermdev.cshop.data.service.ItemService;
import io.ermdev.cshop.data.entity.Category;
import io.ermdev.cshop.data.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"cartItems", "hasUser", "userName"})
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
    public String showCatalog(ModelMap modelMap,
                       @RequestParam(required = false, value = "q") String query,
                       @RequestParam(required = false, value = "c") Long categoryId,
                       @RequestParam(required = false, value = "p") Long parentId,
                       @RequestParam(required = false, value = "page") Integer page) {
        try {
            final List<Item> items = new ArrayList<>();
            final List<Category> categories = new ArrayList<>();
            long itemCount=0;
            int pageCount=0;

            if(query != null) {
                items.addAll(itemService.findByName(query));
                itemCount= (long) items.size();
            } else if(categoryId != null) {
                items.addAll(itemHelper.searchFromCategories(categoryHelper.startWith(categoryId)));
                itemCount= (long) items.size();
            } else {
                itemCount= itemService.countAll();
                pageCount = (int) (itemCount/20);
                pageCount = pageCount * 20 == itemCount ? pageCount : pageCount+1;

                if(page == null || page < 1 || page > pageCount)
                    page=1;
                if(page == 1)
                    items.addAll(itemService.findAll(1, 20));
                else
                    items.addAll(itemService.findAll(((page-1) * 20) + 1, 20));
            }
            if(parentId != null) {
                categories.addAll(categoryService.findByParent(parentId));
            } else {
                categories.add(categoryService.findById(1L));
            }

            if(page == null || page < 1 || page > pageCount)
                page=1;

            modelMap.addAttribute("items", items);
            modelMap.addAttribute("itemCount", itemCount);
            modelMap.addAttribute("pageCount", pageCount);
            modelMap.addAttribute("category", "All");
            modelMap.addAttribute("categories", categories);
            modelMap.addAttribute("page", page);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        return "catalog";
    }

    @PostMapping("catalog/show/modal")
    public String showCatalogModal(@RequestParam("itemId") Long itemId, Model model) {
        try {
            Item item = itemService.findById(itemId);
            model.addAttribute("item", item);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return "modal/cart-modal";
    }


}
