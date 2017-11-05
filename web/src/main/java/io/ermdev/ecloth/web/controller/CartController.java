package io.ermdev.ecloth.web.controller;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.ItemService;
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
public class CartController {

    private ItemService itemService;

    @Autowired
    public CartController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/cart-add")
    public String addToCart(@RequestParam("itemId") Long itemId, ModelMap modelMap) {
        List<Item> items = new ArrayList<>();
        Object sessionObject = modelMap.get("cartItems");
        if(sessionObject != null) {
            for (Object item : (ArrayList) sessionObject) {
                items.add((Item) item);
            }
        }
        try {
            items.add(itemService.findById(itemId));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        modelMap.addAttribute("cartItems", items);
        return "header";
    }
}
