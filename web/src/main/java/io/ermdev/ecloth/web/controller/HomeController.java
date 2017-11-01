package io.ermdev.ecloth.web.controller;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.ItemService;
import io.ermdev.ecloth.model.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"items"})
@RequestMapping
public class HomeController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public String showHomePage(ModelMap modelMap) {


        List<Item> items = null;
        try {
            items = itemService.findAll();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        modelMap.addAttribute("items", items);
        return "home";
    }
}
