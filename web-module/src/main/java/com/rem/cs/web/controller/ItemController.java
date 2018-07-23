package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.item.Item;
import com.rem.cs.data.jpa.item.ItemService;
import com.rem.cs.data.jpa.item.ItemSpecificationBuilder;
import com.rem.cs.exception.EntityException;
import com.rem.cs.web.dto.ItemDto;
import com.rem.cs.web.util.PageHelper;
import com.rem.mappyfy.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String getCatalog(@RequestParam(value = "search", required = false) String search,
                             @PageableDefault(sort = "name", size = 1) Pageable pageable, Model model) {

        final ItemSpecificationBuilder builder = new ItemSpecificationBuilder();
        final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        final Matcher matcher = pattern.matcher(search + ",");

        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        try {
            final Mapper mapper = new Mapper();
            final List<ItemDto> items = new ArrayList<>();
            final Page<Item> pageItems = itemService.findAll(builder.build(), pageable);
            final int currentPage = pageable.getPageNumber() + 1;

            pageItems.forEach(item -> items.add(mapper
                    .set(item)
                    .ignore("categories")
                    .mapTo(ItemDto.class)));

            final PageHelper helper;
            if (search == null) {
                 helper = new PageHelper(currentPage, pageItems);
            } else {
                helper = new PageHelper(currentPage, pageItems, "search=" + search);
            }

            model.addAttribute("items", items);
            model.addAttribute("pages", helper.getPages());
            model.addAttribute("pageNext", helper.getPageNext());
            model.addAttribute("pagePrev", helper.getPagePrev());
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("prevEllipsis", helper.hasPrevEllipsis());
            model.addAttribute("nextEllipsis", helper.hasNextEllipsis());
            model.addAttribute("isFirst", pageItems.isFirst());
            model.addAttribute("isLast", pageItems.isLast());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "catalog";
    }

    @GetMapping("/item/{itemId}.html")
    public String getItemDetail(@PathVariable("itemId") String itemId, Model model) {
        try {
            model.addAttribute("item", itemService.findById(itemId));
        } catch (EntityException e) {
            e.printStackTrace();
        }
        return "item-detail";
    }
}
