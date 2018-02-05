package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.entity.Item;
import io.ermdev.cshop.data.service.ItemService;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"cartItems", "hasUser", "userName"})
public class CatalogController {

    private final ItemService itemService;

    @Autowired
    public CatalogController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("catalog")
    public String showCatalog(ModelMap modelMap, @RequestParam(required = false, value = "page") Integer page) {
        final long ITEM_PER_PAGE = 20;
        try {
            page = 1;
            modelMap.addAttribute("page", page);
            modelMap.addAttribute("items", itemService.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "catalog";
    }

    @PostMapping("catalog/show/modal")
    public String showCatalogModal(@RequestParam("itemId") Long itemId, Model model) {
        try {
            Item item = itemService.findById(itemId);
            model.addAttribute("item", item);
        } catch (EntityException e) {
            e.printStackTrace();
        }
        return "modal/cart-modal";
    }
}
