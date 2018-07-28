package com.rem.cs.web.controller;

import com.rem.cs.data.jpa.category.Category;
import com.rem.cs.data.jpa.category.CategoryService;
import com.rem.cs.rest.client.item.Item;
import com.rem.cs.rest.client.item.ItemService;
import com.rem.cs.web.domain.Page;
import com.rem.cs.web.dto.CategoryDto;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@SessionAttributes({"signedInUser", "cartItems"})
public class CatalogController {

    private final Mapper mapper = new Mapper();
    private final ItemService itemService;
    private final CategoryService categoryService;

    @Autowired
    public CatalogController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("cartItems")
    public List<Item> setUpCartItems() {
        return new ArrayList<>();
    }

    @ModelAttribute(name = "categories")
    public List<CategoryDto> setUpCategories() {
        final List<Category> categories = categoryService.findByParentIsNull();

        return mapper.from(categories).toListOf(CategoryDto.class);
    }

    @GetMapping("/catalog")
    public CharSequence clickSearch(@RequestParam(value = "search") String search,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @PageableDefault(sort = {"name"}, size = 20) Pageable pageable, Model model) {
        final PagedResources<Item> resources = itemService.getAll(pageable.getPageNumber() + 1, search);
        final int currentPage = (int) resources.getMetadata().getNumber();
        final int totalPages = (int) resources.getMetadata().getTotalPages();
        final Page[] pages = new Page[totalPages < 5 ? totalPages : 5];

        final Page pagePrev = new Page(currentPage - 1, linkTo(methodOn(getClass())
                .clickSearch(search, currentPage - 1, pageable, model)).toUri().toString());

        final Page pageNext = new Page(currentPage + 1, linkTo(methodOn(getClass())
                .clickSearch(search, currentPage + 1, pageable, model)).toUri().toString());

        if (pages.length == 5) {
            final Page pageFirst = new Page(1, linkTo(methodOn(getClass())
                    .clickSearch(search, 1, pageable, model)).toUri().toString());
            final Page pageLast = new Page(totalPages, linkTo(methodOn(getClass())
                    .clickSearch(search, totalPages, pageable, model)).toUri().toString());

            if (currentPage < pages.length - 1) {
                for (int i = 0; i < pages.length; i++) {
                    final int pNum = i + 1;
                    final String href = linkTo(methodOn(getClass())
                            .clickSearch(search, pNum, pageable, model)).toUri().toString();
                    pages[i] = new Page(pNum, href);
                }
                if (totalPages > pages.length) {
                    model.addAttribute("pageLast", pageLast);
                }
            } else {
                final int start = currentPage - 2;

                if (currentPage + 2 >= totalPages) {
                    for (int i = 0; i < pages.length; i++) {
                        final int index = (5 - 1) - i;
                        final int pNum = totalPages - i;
                        final String href = linkTo(methodOn(getClass())
                                .clickSearch(search, pNum, pageable, model)).toUri().toString();
                        pages[index] = new Page(pNum, href);
                    }
                    model.addAttribute("pageFirst", pageFirst);
                } else {
                    for (int i = 0; i < pages.length; i++) {
                        final int pNum = start + i;
                        final String href = linkTo(methodOn(getClass())
                                .clickSearch(search, pNum, pageable, model)).toUri().toString();
                        pages[i] = new Page(pNum, href);
                    }
                    model.addAttribute("pageFirst", pageFirst);
                    model.addAttribute("pageLast", pageLast);
                }
            }
        } else {
            for (int i = 0; i < pages.length; i++) {
                final int pNum = i + 1;
                final String href = linkTo(methodOn(getClass())
                        .clickSearch(search, pNum, pageable, model)).toUri().toString();
                pages[i] = new Page(pNum, href);
            }
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", totalPages);
        model.addAttribute("pagePrev", pagePrev);
        model.addAttribute("pageNext", pageNext);
        model.addAttribute("pages", pages);
        model.addAttribute("items", resources.getContent());
        return "catalog";
    }

    @GetMapping("/item/{itemId}.html")
    public CharSequence clickItem(@PathVariable("itemId") String itemId, Model model) {
        final Item item = new ItemService().getById(itemId);

        if (item != null) {
            model.addAttribute("item", item);
        }
        return "item-detail";
    }
}
