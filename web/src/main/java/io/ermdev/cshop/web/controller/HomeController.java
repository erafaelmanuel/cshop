package io.ermdev.cshop.web.controller;

import io.ermdev.cshop.data.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"cartItems", "hasUser", "userName"})
@RequestMapping
public class HomeController {

    private ItemService itemService;

    @Autowired
    public HomeController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String showHomePage(ModelMap modelMap) {
        return "index";
    }
}
