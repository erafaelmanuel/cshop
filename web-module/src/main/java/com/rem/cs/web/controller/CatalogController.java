package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.category.Category;
import com.rem.cs.data.jpa.category.CategoryService;
import com.rem.cs.rest.client.item.Item;
import com.rem.cs.rest.client.item.ItemService;
import com.rem.cs.web.dto.CategoryDto;
import com.rem.cs.web.dto.ItemDto;
import com.rem.cs.web.dto.Page;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"signedInUser", "cartItems"})
public class CatalogController {

    private final ItemService itemService;
    private final CategoryService categoryService;
    private final Mapper mapper;

    @Autowired
    public CatalogController(CategoryService categoryService) {
        this.itemService = new ItemService();
        this.categoryService = categoryService;
        this.mapper = new Mapper();
    }

    @ModelAttribute("cartItems")
    public List<ItemDto> setUpCartItems() {
        return new ArrayList<>();
    }

    @ModelAttribute(name = "categories")
    public List<CategoryDto> setUpCategories() {
        final List<Category> categories = categoryService.findByParenIsNull();

        return mapper.from(categories).toListOf(CategoryDto.class);
    }

    @GetMapping("/catalog")
    public String catalog(@RequestParam(value = "search", required = false) String search,
                          @PageableDefault(sort = {"name"}, size = 20) Pageable pageable, Model model) {
        final PagedResources<Item> resources = itemService.getAll(pageable.getPageNumber() + 1, search);
        final int length = (int) resources.getMetadata().getTotalPages() < 5 ?
                (int) resources.getMetadata().getTotalPages() : 5;
        final Page[] pages = new Page[length];

        if (pages.length == 5) {
            if (resources.getMetadata().getNumber() < pages.length - 1) {
                for (int i = 0; i < pages.length; i++) {
                    final int number = i + 1;
                    final String href = "".concat("?page=" + number);

                    pages[i] = new Page(number, href);
                }
                if (resources.getMetadata().getTotalPages() > pages.length) {
                    model.addAttribute("pageLast", new Page(resources.getMetadata().getTotalPages(), ""
                            .concat("?page=" + resources.getMetadata().getTotalPages())
                            .concat(search != null ? "&search=" + search : "")));
                }
            } else {
                final int start = (int) resources.getMetadata().getNumber() - 2;

                if (resources.getMetadata().getNumber() + 2 >= resources.getMetadata().getTotalPages()) {
                    for (int i = 0; i < pages.length; i++) {
                        final int index = (5 - 1) - i;
                        final int number = (int) resources.getMetadata().getTotalPages() - i;
                        final String href = "".concat("?page=" + number);

                        pages[index] = new Page(number, href);
                    }
                    model.addAttribute("pageFirst", new Page(1, ""
                            .concat("?page=1")
                            .concat(search != null ? "&search=" + search : "")));
                } else {
                    for (int i = 0; i < pages.length; i++) {
                        final int number = start + i;
                        final String href = "".concat("?page=" + number);

                        pages[i] = new Page(number, href);
                    }
                    model.addAttribute("pageFirst", new Page(1, ""
                            .concat("?page=1")
                            .concat(search != null ? "&search=" + search : "")));
                    model.addAttribute("pageLast", new Page(resources.getMetadata().getTotalPages(), ""
                            .concat("?page=" + resources.getMetadata().getTotalPages())
                            .concat(search != null ? "&search=" + search : "")));
                }
            }
        } else {
            for (int i = 0; i < pages.length; i++) {
                final int number = i + 1;
                final String href = "".concat("?page=" + number);

                pages[i] = new Page(number, href);
            }
        }
        model.addAttribute("pagePrev", new Page(resources.getMetadata().getNumber() - 1, ""
                .concat("?page=" + (resources.getMetadata().getNumber() - 1))
                .concat(search != null ? "&search=" + search : "")));
        model.addAttribute("pageNext", new Page(resources.getMetadata().getNumber() + 1, ""
                .concat("?page=" + (resources.getMetadata().getNumber() + 1))
                .concat(search != null ? "&search=" + search : "")));

        model.addAttribute("currentPage", resources.getMetadata().getNumber());
        model.addAttribute("totalPage", resources.getMetadata().getTotalPages());
        model.addAttribute("items", resources.getContent());
        model.addAttribute("pages", pages);
        return "catalog";
    }

    @GetMapping("/item/{itemId}.html")
    public String item(@PathVariable("itemId") String itemId, Model model) {
        final Item item = new ItemService().getById(itemId);

        if (item != null) {
            model.addAttribute("item", item);
        }
        return "item-detail";
    }
}
