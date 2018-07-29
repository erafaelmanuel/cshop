package com.rem.cs.web.controller;

import com.rem.cs.exception.EntityException;
import com.rem.cs.rest.client.resource.item.Item;
import com.rem.cs.rest.client.resource.item.ItemService;
import com.rem.cs.web.dto.ItemDto;
import com.rem.mappyfy.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"cartItems"})
public class CartController {

    private ItemService itemService;

    public CartController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ModelAttribute("cartItems")
    public List<Item> initCartItems() {
        return new ArrayList<>();
    }

    @PostMapping("/cart/add")
    public String addItem(@RequestParam("itemId") String itemId,
                          @SessionAttribute(name = "cartItems") List<Item> cartItems, Model model) {
        cartItems.add(itemService.findById(itemId).getContent());
        model.addAttribute("cartItems", cartItems);
        return "fragment/nav/cart";
    }
}
