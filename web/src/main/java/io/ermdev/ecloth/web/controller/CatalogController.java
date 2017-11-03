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
@SessionAttributes({"sessionItems"})
public class CatalogController {

    private ItemService itemService;

    @Autowired
    public CatalogController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("catalog")
    public String search(ModelMap modelMap, @RequestParam(required = false, value = "q") String q) {
        try {
            final List<Item> items = new ArrayList<>();
            if(q != null)
                items.addAll(itemService.findByName(q));
            else
                items.addAll(itemService.findAll());
            modelMap.addAttribute("items", items);
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }

        return "catalog";
    }
}
