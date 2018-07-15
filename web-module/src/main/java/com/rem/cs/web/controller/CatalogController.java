package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.item.ItemService;
import com.rem.cs.web.dto.ItemDto;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"verifiedUser", "cartItems"})
public class CatalogController {

    private final ItemService itemService;

    @Autowired
    public CatalogController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/catalog")
    public String getCatalog(@PageableDefault(sort = "name") Pageable pageable, Model model) {
        try {
            final List<ItemDto> items = new ArrayList<>();
            final Mapper mapper = new Mapper();

            itemService.findAll(pageable).forEach(item -> items.add(
                    mapper
                            .set(item)
                            .ignore("categories")
                            .mapTo(ItemDto.class)));
            model.addAttribute("items", items);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "catalog";
    }
}
