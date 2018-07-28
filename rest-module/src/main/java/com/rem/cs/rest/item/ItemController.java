package com.rem.cs.rest.item;

import com.rem.cs.commons.NumberUtils;
import com.rem.cs.data.jpa.item.Item;
import com.rem.cs.data.jpa.item.ItemService;
import com.rem.cs.data.jpa.item.ItemSpecificationBuilder;
import com.rem.cs.exception.EntityException;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> getAll(@RequestParam(name = "search", required = false) String search,
                                    @RequestParam(name = "page", required = false) Integer page,
                                    @RequestParam(name = "size", required = false) Integer size,
                                    @RequestParam(name = "sort", required = false) String sort) {
        final ItemSpecificationBuilder builder = new ItemSpecificationBuilder();
        final List<ItemDto> items = new ArrayList<>();
        final Mapper mapper = new Mapper();

        final int tempPage = NumberUtils.getOrElse(page, 0).intValue();
        final int tempSize = NumberUtils.getOrElse(size, 1).intValue();
        final String tempSort = !StringUtils.isEmpty(sort) ? sort : "name";

        final Pageable pageable = PageRequest.of(tempPage, tempSize, Sort.by(tempSort));

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

        resources = new PagedResources<>(items, new PagedResources.PageMetadata(pageable.getPageSize(), pageable
                .getPageNumber(), pageItems.getTotalElements(), pageItems.getTotalPages()));
        resources.add(linkTo(methodOn(getClass()).getAll(search, page, size, sort)).withSelfRel());
        if (pageItems.getTotalPages() > 1) {
            resources.add(linkTo(methodOn(getClass()).getAll(search, 1, size, sort)).withRel("first"));
            resources.add(linkTo(methodOn(getClass()).getAll(search, pageItems.getTotalPages(), size, sort))
                    .withRel("last"));
            if (!pageItems.isFirst()) {
                resources.add(linkTo(methodOn(getClass()).getAll(search, (page != null) ? page - 1 : null, size, sort))
                        .withRel("prev"));
            }
            if (!pageItems.isLast()) {
                resources.add(linkTo(methodOn(getClass()).getAll(search, (page != null) ? page + 1 : null, size, sort))
                        .withRel("next"));
            }
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(value = {"/{itemId}"}, produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> getById(@PathVariable("itemId") String itemId) {
        try {
            final Mapper mapper = new Mapper();
            final ItemDto resource = mapper.from(itemService.findById(itemId)).toInstanceOf(ItemDto.class);

            resource.add(linkTo(methodOn(getClass()).getById(itemId)).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (EntityException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
