package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.item.Item;
import com.rem.cs.data.jpa.item.ItemService;
import com.rem.cs.web.dto.ItemDto;
import com.rem.mappyfy.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"signedInUser", "cartItems"})
public class ItemController {

    private ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ModelAttribute("cartItems")
    public List<ItemDto> setUpCartItems() {
        return new ArrayList<>();
    }

    @GetMapping("/catalog")
    public String getCatalog(@PageableDefault(sort = "name", size = 20) Pageable pageable, Model model) {
        try {
            final Mapper mapper = new Mapper();
            final List<ItemDto> items = new ArrayList<>();
            final Page<Item> pageItems = itemService.findAll(pageable);
            final int pages[] = new int[pageItems.getTotalPages() < 5 ? pageItems.getTotalPages() : 5];

            int currentPage = pageable.getPageNumber() + 1;
            boolean prevEllipsis = false;
            boolean nextEllipsis = false;

            pageItems.forEach(item -> items.add(mapper
                    .set(item)
                    .ignore("categories")
                    .mapTo(ItemDto.class)));
            if (currentPage > pageItems.getTotalPages()) {
                currentPage = 1;
            }
            if (pages.length == 5) {
                if (currentPage < pages.length - 1) {
                    for (int i = 0; i < pages.length; i++) {
                        pages[i] = i + 1;
                    }
                    if (pageItems.getTotalPages() > pages.length) {
                        nextEllipsis = true;
                    }
                } else {
                    final int start = currentPage - 2;

                    if (currentPage + 2 >= pageItems.getTotalPages()) {
                        for (int i = 0; i < pages.length; i++) {
                            pages[(pages.length - 1) - i] = pageItems.getTotalPages() - i;
                        }
                        prevEllipsis = true;
                    } else {
                        for (int i = 0; i < pages.length; i++) {
                            pages[i] = start + i;
                        }
                        prevEllipsis = true;
                        nextEllipsis = true;
                    }
                }
            } else {
                for (int i = 0; i < pages.length; i++) {
                    pages[i] = i + 1;
                }
            }

            model.addAttribute("items", items);
            model.addAttribute("pages", pages);
            model.addAttribute("cp", currentPage);
            model.addAttribute("prevEllipsis", prevEllipsis);
            model.addAttribute("nextEllipsis", nextEllipsis);
            model.addAttribute("isFirst", pageItems.isFirst());
            model.addAttribute("isLast", pageItems.isLast());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "catalog";
    }

    @GetMapping("/item/{itemId}")
    public String getItemDetail(@PathVariable("itemId") String itemId) {
        return "item-detail";
    }
}
