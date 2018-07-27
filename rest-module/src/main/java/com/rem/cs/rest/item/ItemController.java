package com.rem.cs.rest.item;

import com.rem.cs.data.jpa.item.Item;
import com.rem.cs.data.jpa.item.ItemService;
import com.rem.cs.data.jpa.item.ItemSpecificationBuilder;
import com.rem.cs.exception.EntityException;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final Mapper mapper;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
        this.mapper = new Mapper();
    }

    @GetMapping(produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> getAll(@PageableDefault(sort = {"name"}, size = 1) Pageable pageable,
                                    @RequestParam(name = "search", required = false) String search) {
        final List<ItemDto> items = new ArrayList<>();
        final ItemSpecificationBuilder builder = new ItemSpecificationBuilder();

        final PagedResources<ItemDto> resources;
        final Page<Item> pageItems;

        if (!StringUtils.isEmpty(search)) {
            final Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            final Matcher matcher = pattern.matcher(search + ",");

            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            if (builder.getParamSize() == 0) {
                builder.with("name", ":", search);
            }
        }

        pageItems = itemService.findAll(builder.build(), pageable);
        pageItems.forEach(item -> {
            final ItemDto dto = mapper.from(item).toInstanceOf(ItemDto.class);

            dto.add(linkTo(methodOn(getClass()).getById(dto.getUid())).withSelfRel());
            items.add(dto);
        });
        resources = new PagedResources<>(items, new PagedResources.PageMetadata(pageable.getPageSize(),
                pageable.getPageNumber() + 1, pageItems.getTotalElements(), pageItems.getTotalPages()));

        resources.add(linkTo(getClass())
                .slash(search != null ? "?search=" + search : "")
                .withSelfRel());
        if (pageItems.getTotalPages() > 1) {
            resources.add(linkTo(getClass()).slash("?page=1")
                    .slash(search != null ? "&search=" + search : "")
                    .withRel(Link.REL_FIRST));
            resources.add(linkTo(getClass()).slash("?page=" + pageItems.getTotalPages())
                    .slash(search != null ? "&search=" + search : "")
                    .withRel(Link.REL_LAST));
        }
        if (!pageItems.isFirst()) {
            resources.add(linkTo(getClass()).slash("?page=" + pageable.getPageNumber())
                    .slash(search != null ? "&search=" + search : "")
                    .withRel(Link.REL_PREVIOUS));
        }
        if (!pageItems.isLast()) {
            resources.add(linkTo(getClass()).slash("?page=" + (pageable.getPageNumber() + 2))
                    .slash(search != null ? "&search=" + search : "")
                    .withRel(Link.REL_NEXT));
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(value = {"/{itemId}"}, produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> getById(@PathVariable("itemId") String itemId) {
        try {
            final Mapper mapper = new Mapper();
            final ItemDto dto = mapper.from(itemService.findById(itemId)).toInstanceOf(ItemDto.class);

            dto.add(linkTo(methodOn(getClass()).getById(itemId)).withSelfRel());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
