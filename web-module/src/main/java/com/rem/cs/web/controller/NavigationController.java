package com.rem.cs.web.controller;

import com.rem.cs.rest.client.resource.item.Item;
import com.rem.cs.rest.client.resource.item.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("navigationWebController")
@SessionAttributes({"cartItems"})
public class NavigationController {

    private ItemService itemService;

    @Autowired
    public NavigationController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ModelAttribute("cartItems")
    public List<Item> initCartItems() {
        return new ArrayList<>();
    }

    @PostMapping("/cart/add")
    public String doAddItem(@RequestParam("itemId") String itemId,
                            @SessionAttribute(name = "cartItems") List<Item> cartItems, Model model) {
        cartItems.add(itemService.findById(itemId).getContent());
        model.addAttribute("cartItems", cartItems);
        return "fragment/nav/cart";
    }

}
