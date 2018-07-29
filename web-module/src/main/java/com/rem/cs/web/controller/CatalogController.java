package com.rem.cs.web.controller;

import com.rem.cs.rest.client.resource.client.Category;
import com.rem.cs.rest.client.resource.client.CategoryService;
import com.rem.cs.rest.client.resource.item.Item;
import com.rem.cs.rest.client.resource.item.ItemService;
import com.rem.cs.web.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
@SessionAttributes({"signedInUser", "cartItems"})
public class CatalogController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @Autowired
    public CatalogController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("cartItems")
    public List<Item> initItems() {
        return new ArrayList<>();
    }

    @ModelAttribute(name = "categories")
    public List<Category> initCategories() {
        return new ArrayList<>(categoryService.findByParentIsNull(0, 0, null).getContent());
    }

    @GetMapping("/catalog")
    public CharSequence clickSearch(@RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "size", required = false) Integer size,
                                    @RequestParam(value = "sort", required = false) String sort,
                                    Model model) throws URISyntaxException {

        final PagedResources<Item> resources = itemService.findAll(search, page, size, sort);
        final int currentPage = (int) resources.getMetadata().getNumber();
        final int totalPages = (int) resources.getMetadata().getTotalPages();

        final Page[] pages = new Page[totalPages < 5 ? totalPages : 5];
        final Map<String, Object> parameters = new HashMap<>();

        boolean hasFirst = false;
        boolean hasLast = false;

        parameters.put("search", search);
        parameters.put("size", size);
        parameters.put("sort", sort);

        if (pages.length == 5) {
            if (currentPage < pages.length - 1) {
                for (int i = 0; i < pages.length; i++) {
                    final int pNum = i + 1;

                    pages[i] = new Page(pNum, "?".concat(linkTo(methodOn(getClass())
                            .clickSearch(search, pNum, size, sort, model)).toUri().getQuery()));
                }
                if (totalPages > pages.length) {
                    hasLast = true;
                }
            } else {
                final int start = currentPage - 2;

                if (currentPage + 2 >= totalPages) {
                    for (int i = 0; i < pages.length; i++) {
                        final int pNum = totalPages - i;

                        pages[(pages.length - 1) - i] = new Page(pNum, "?".concat(linkTo(methodOn(getClass())
                                .clickSearch(search, pNum, size, sort, model)).toUri().getQuery()));
                    }
                    hasFirst = true;
                } else {
                    for (int i = 0; i < pages.length; i++) {
                        final int pNum = start + i;

                        pages[i] = new Page(pNum, "?".concat(linkTo(methodOn(getClass())
                                .clickSearch(search, pNum, size, sort, model)).toUri().getQuery()));
                    }
                    hasFirst = true;
                    hasLast = true;
                }
            }
        } else {
            for (int i = 0; i < pages.length; i++) {
                final int pNum = i + 1;

                pages[i] = new Page(pNum, "?".concat(linkTo(methodOn(getClass())
                        .clickSearch(search, pNum, size, sort, model)).toUri().getQuery()));
            }
        }
        if (resources.hasLinks()) {
            if (hasFirst && resources.getLink("first") != null) {
                model.addAttribute("pageFirst", new Page(1, "?".concat(new URI(resources
                        .getLinks("first").get(0).expand(parameters).getHref()).getQuery())));
            }
            if (hasLast && resources.getLink("last") != null) {
                model.addAttribute("pageLast", new Page(totalPages, "?".concat(new URI(resources
                        .getLinks("last").get(0).expand(parameters).getHref()).getQuery())));
            }
            if (resources.getPreviousLink() != null) {
                model.addAttribute("pagePrev", new Page((currentPage - 1), "?".concat(new URI(resources
                        .getPreviousLink().expand(parameters).getHref()).getQuery())));
            }
            if (resources.getNextLink() != null) {
                model.addAttribute("pageNext", new Page((currentPage + 1), "?".concat(new URI(resources
                        .getNextLink().expand(parameters).getHref()).getQuery())));
            }
        }
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPage", totalPages);
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
