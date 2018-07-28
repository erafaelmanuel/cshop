package com.rem.cs.rest.item;

import com.rem.cs.commons.NumberUtils;
import com.rem.cs.data.jpa.category.Category;
import com.rem.cs.data.jpa.item.Item;
import com.rem.cs.data.jpa.item.ItemJpaRepository;
import com.rem.cs.data.jpa.item.ItemSpecificationBuilder;
import com.rem.cs.exception.EntityException;
import com.rem.cs.rest.category.CategoryDto;
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
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private ItemJpaRepository itemRepo;

    @Autowired
    public ItemController(ItemJpaRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @GetMapping(produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findAll(@RequestParam(name = "search", required = false) String search,
                                     @RequestParam(name = "page", required = false) Integer page,
                                     @RequestParam(name = "size", required = false) Integer size,
                                     @RequestParam(name = "sort", required = false) String sort) {
        final ItemSpecificationBuilder builder = new ItemSpecificationBuilder();
        final List<ItemDto> items = new ArrayList<>();
        final Mapper mapper = new Mapper();

        final int tempPage = NumberUtils.indexOfZero(page);
        final int tempSize = NumberUtils.getOrElse(size, 20).intValue();
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
        pageItems = itemRepo.findAll(pageable);
        pageItems.forEach(item -> {
            final ItemDto dto = mapper.from(item).toInstanceOf(ItemDto.class);

            dto.add(linkTo(methodOn(getClass()).getById(dto.getUid())).withSelfRel());
            dto.add(linkTo(methodOn(getClass()).findCategoriesById(dto.getUid(), null, null, null))
                    .withRel("categories"));
            items.add(dto);
        });
        resources = new PagedResources<>(items, new PagedResources.PageMetadata(pageable.getPageSize(), pageable
                .getPageNumber(), pageItems.getTotalElements(), pageItems.getTotalPages()));

        resources.add(linkTo(methodOn(getClass()).findAll(search, page, size, sort)).withSelfRel());
        if (pageItems.getTotalPages() > 1) {
            resources.add(linkTo(methodOn(getClass()).findAll(search, 1, size, sort)).withRel("first"));
            resources.add(linkTo(methodOn(getClass()).findAll(search, pageItems.getTotalPages(), size, sort))
                    .withRel("last"));
        }
        if ((tempPage + 1) > 1 && (tempPage + 1) <= pageItems.getTotalPages() && !pageItems.isFirst()) {
            resources.add(linkTo(methodOn(getClass()).findAll(search, (tempPage + 1) - 1, size, sort))
                    .withRel("prev"));
        }
        if (!pageItems.isLast()) {
            resources.add(linkTo(methodOn(getClass()).findAll(search, (tempPage + 1) + 1, size, sort))
                    .withRel("next"));
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(value = {"/{itemId}"}, produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> getById(@PathVariable("itemId") String itemId) {
        final Mapper mapper = new Mapper();
        final Optional<Item> item = itemRepo.findById(itemId);

        try {
            final ItemDto resource = mapper.from(item.orElseThrow(() -> new EntityException("No item found")))
                    .toInstanceOf(ItemDto.class);

            resource.add(linkTo(methodOn(getClass()).getById(itemId)).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (EntityException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = {"/search/findByCategoryId"}, produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findByCategoryId(@RequestParam(name = "categoryId") List<String> categoryIds,
                                              @RequestParam(name = "page", required = false) Integer page,
                                              @RequestParam(name = "size", required = false) Integer size,
                                              @RequestParam(name = "sort", required = false) String sort) {
        final List<ItemDto> items = new ArrayList<>();
        final Mapper mapper = new Mapper();

        final int tempPage = NumberUtils.indexOfZero(page);
        final int tempSize = NumberUtils.getOrElse(size, 20).intValue();
        final String tempSort = !StringUtils.isEmpty(sort) ? sort : "name";

        final Pageable pageable = PageRequest.of(tempPage, tempSize, Sort.by(tempSort));
        final Page<Item> pageItems = itemRepo.findByCategoryId(categoryIds, pageable);

        final PagedResources<ItemDto> resources;

        pageItems.forEach(item -> {
            final ItemDto dto = mapper.from(item).toInstanceOf(ItemDto.class);

            dto.add(linkTo(methodOn(getClass()).getById(dto.getUid())).withSelfRel());
            items.add(dto);
        });
        resources = new PagedResources<>(items, new PagedResources.PageMetadata(pageable.getPageSize(), pageable
                .getPageNumber(), pageItems.getTotalElements(), pageItems.getTotalPages()));

        resources.add(linkTo(methodOn(getClass()).findByCategoryId(categoryIds, page, size, sort)).withSelfRel());
        if (pageItems.getTotalPages() > 1) {
            resources.add(linkTo(methodOn(getClass()).findByCategoryId(categoryIds, 1, size, sort))
                    .withRel("first"));
            resources.add(linkTo(methodOn(getClass()).findByCategoryId(categoryIds, pageItems
                    .getTotalPages(), size, sort)).withRel("last"));
        }
        if ((tempPage + 1) > 1 && (tempPage + 1) <= pageItems.getTotalPages() && !pageItems.isFirst()) {
            resources.add(linkTo(methodOn(getClass()).findByCategoryId(categoryIds, (tempPage + 1) - 1, size, sort))
                    .withRel("prev"));
        }
        if (!pageItems.isLast()) {
            resources.add(linkTo(methodOn(getClass()).findByCategoryId(categoryIds, (tempPage + 1) + 1, size, sort))
                    .withRel("next"));
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(value = {"/{itemId}/categories"}, produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findCategoriesById(@PathVariable("itemId") String itemId,
                                                @RequestParam(name = "page", required = false) Integer page,
                                                @RequestParam(name = "size", required = false) Integer size,
                                                @RequestParam(name = "sort", required = false) String sort) {
        final List<CategoryDto> categories = new ArrayList<>();
        final Mapper mapper = new Mapper();

        final int tempPage = NumberUtils.getOrElse(page, 0).intValue();
        final int tempSize = NumberUtils.getOrElse(size, 20).intValue();
        final String tempSort = !StringUtils.isEmpty(sort) ? sort : "name";

        final Pageable pageable = PageRequest.of(tempPage, tempSize, Sort.by(tempSort));
        final Page<Category> pageCategories = itemRepo.findCategoriesById(itemId, pageable);

        final PagedResources<CategoryDto> resources;

        pageCategories.forEach(category -> {
            final CategoryDto dto = mapper.from(category).toInstanceOf(CategoryDto.class);

            categories.add(dto);
        });
        resources = new PagedResources<>(categories, new PagedResources.PageMetadata(pageable.getPageSize(), pageable
                .getPageNumber(), pageCategories.getTotalElements(), pageCategories.getTotalPages()));

        if (pageCategories.getTotalPages() > 1) {
            resources.add(linkTo(methodOn(getClass()).findCategoriesById(itemId, 1, size, sort))
                    .withRel("first"));
            resources.add(linkTo(methodOn(getClass()).findCategoriesById(itemId, pageCategories
                    .getTotalPages(), size, sort)).withRel("last"));
        }
        if ((tempPage + 1) > 1 && (tempPage + 1) <= pageCategories.getTotalPages() && !pageCategories.isFirst()) {
            resources.add(linkTo(methodOn(getClass()).findCategoriesById(itemId, (tempPage + 1) - 1, size, sort))
                    .withRel("prev"));
        }
        if (!pageCategories.isLast()) {
            resources.add(linkTo(methodOn(getClass()).findCategoriesById(itemId, (tempPage + 1) + 1, size, sort))
                    .withRel("next"));
        }
        resources.add(linkTo(methodOn(getClass()).findCategoriesById(itemId, page, size, sort)).withSelfRel());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
