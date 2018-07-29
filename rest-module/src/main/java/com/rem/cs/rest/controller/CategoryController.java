package com.rem.cs.rest.controller;

import com.rem.cs.commons.NumberUtils;
import com.rem.cs.data.jpa.entity.Category;
import com.rem.cs.data.jpa.repository.CategoryRepository;
import com.rem.cs.data.jpa.service.CategoryService;
import com.rem.cs.data.jpa.specification.EntitySpecificationBuilder;
import com.rem.cs.exception.EntityException;
import com.rem.cs.rest.dto.CategoryDto;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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

@RestController("categoryRestController")
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryRepository categoryRepo;
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryRepository categoryRepo, CategoryService categoryService) {
        this.categoryRepo = categoryRepo;
        this.categoryService = categoryService;
    }

    @GetMapping(produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findAll(@RequestParam(name = "search", required = false) String search,
                                     @RequestParam(name = "page", required = false) Integer page,
                                     @RequestParam(name = "size", required = false) Integer size,
                                     @RequestParam(name = "sort", required = false) String sort) {
        final EntitySpecificationBuilder<Category> builder = new EntitySpecificationBuilder<>();
        final List<CategoryDto> categories = new ArrayList<>();
        final Mapper mapper = new Mapper();

        final int tempPage = NumberUtils.zeroBased(page);
        final int tempSize = NumberUtils.getOrDefault(size, 20).intValue();
        final String tempSort = !StringUtils.isEmpty(sort) ? sort : "name";

        final Pageable pageable = PageRequest.of(tempPage, tempSize, Sort.by(tempSort));

        final Page<Category> pageCategories;
        final PagedResources<CategoryDto> resources;

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

        pageCategories = categoryRepo.findAll(builder.build(), pageable);
        pageCategories.forEach(category -> {
            final CategoryDto dto = mapper.from(category).toInstanceOf(CategoryDto.class);

            dto.add(linkTo(methodOn(getClass()).findById(dto.getUid())).withSelfRel());
            categories.add(dto);
        });
        resources = new PagedResources<>(categories, new PagedResources.PageMetadata(tempSize, (tempPage + 1),
                pageCategories.getTotalElements(), pageCategories.getTotalPages()));

        resources.add(linkTo(methodOn(getClass()).findAll(search, page, size, sort)).withSelfRel());
        if (pageCategories.getTotalPages() > 1) {
            resources.add(linkTo(methodOn(getClass()).findAll(search, 1, size, sort))
                    .withRel("first"));
            resources.add(linkTo(methodOn(getClass()).findAll(search, pageCategories.getTotalPages(),
                    size, sort)).withRel("last"));
        }
        if ((tempPage + 1) > 1 && (tempPage + 1) <= pageCategories.getTotalPages() && !pageCategories.isFirst()) {
            resources.add(linkTo(methodOn(getClass()).findAll(search, (tempPage + 1) - 1, size, sort))
                    .withRel("prev"));
        }
        if (!pageCategories.isLast()) {
            resources.add(linkTo(methodOn(getClass()).findAll(search, (tempPage + 1) + 1, size, sort))
                    .withRel("next"));
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(value = "{categoryId}", produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findById(@PathVariable("categoryId") String categoryId) {
        final Optional<Category> category = categoryRepo.findById(categoryId);
        final Mapper mapper = new Mapper();
        try {
            final CategoryDto dto = mapper.from(category.orElseThrow(() ->
                    new EntityException("No category found"))).toInstanceOf(CategoryDto.class);
            final Resource<CategoryDto> resource = new Resource<>(dto);

            resource.add(linkTo(methodOn(getClass()).findById(categoryId)).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (EntityException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/search/findByParentIsNull", produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findByParentIsNull(@RequestParam(name = "page", required = false) Integer page,
                                                @RequestParam(name = "size", required = false) Integer size,
                                                @RequestParam(name = "sort", required = false) String sort) {
        final List<CategoryDto> categories = new ArrayList<>();
        final Mapper mapper = new Mapper();

        final int tempPage = NumberUtils.zeroBased(page);
        final int tempSize = NumberUtils.getOrDefault(size, 20).intValue();
        final String tempSort = !StringUtils.isEmpty(sort) ? sort : "name";

        final Pageable pageable = PageRequest.of(tempPage, tempSize, Sort.by(tempSort));
        final Page<Category> pageCategories = categoryRepo.findByParentIsNull(pageable);

        final PagedResources<CategoryDto> resources;

        categoryRepo.findByParentIsNull(pageable).forEach(category -> {
            final CategoryDto dto = mapper.from(category).toInstanceOf(CategoryDto.class);

            dto.add(linkTo(methodOn(getClass()).findById(dto.getUid())).withSelfRel());
            categories.add(dto);
        });
        resources = new PagedResources<>(categories, new PagedResources.PageMetadata(tempSize, (tempPage + 1),
                pageCategories.getTotalElements(), pageCategories.getTotalPages()));

        resources.add(linkTo(methodOn(getClass()).findByParentIsNull(page, size, sort)).withSelfRel());
        if (pageCategories.getTotalPages() > 1) {
            resources.add(linkTo(methodOn(getClass()).findByParentIsNull(1, size, sort))
                    .withRel("first"));
            resources.add(linkTo(methodOn(getClass()).findByParentIsNull(pageCategories.getTotalPages(),
                    size, sort)).withRel("last"));
        }
        if ((tempPage + 1) > 1 && (tempPage + 1) <= pageCategories.getTotalPages() && !pageCategories.isFirst()) {
            resources.add(linkTo(methodOn(getClass()).findByParentIsNull((tempPage + 1) - 1, size, sort))
                    .withRel("prev"));
        }
        if (!pageCategories.isLast()) {
            resources.add(linkTo(methodOn(getClass()).findByParentIsNull((tempPage + 1) + 1, size, sort))
                    .withRel("next"));
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(value = "/search/findByParentId", produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findByParentId(@RequestParam("categoryId") String categoryId,
                                            @RequestParam(name = "page", required = false) Integer page,
                                            @RequestParam(name = "size", required = false) Integer size,
                                            @RequestParam(name = "sort", required = false) String sort) {
        final List<CategoryDto> categories = new ArrayList<>();
        final Mapper mapper = new Mapper();

        final int tempPage = NumberUtils.zeroBased(page);
        final int tempSize = NumberUtils.getOrDefault(size, 20).intValue();
        final String tempSort = !StringUtils.isEmpty(sort) ? sort : "name";

        final Pageable pageable = PageRequest.of(tempPage, tempSize, Sort.by(tempSort));
        final Page<Category> pageCategories = categoryRepo.findByParentIsNull(pageable);

        final PagedResources<CategoryDto> resources;

        categoryRepo.findByParentId(categoryId, pageable).forEach(category -> {
            final CategoryDto dto = mapper.from(category).toInstanceOf(CategoryDto.class);

            dto.add(linkTo(methodOn(getClass()).findById(dto.getUid())).withSelfRel());
            categories.add(dto);
        });
        resources = new PagedResources<>(categories, new PagedResources.PageMetadata(tempSize, (tempPage + 1),
                pageCategories.getTotalElements(), pageCategories.getTotalPages()));

        resources.add(linkTo(methodOn(getClass()).findByParentId(categoryId, page, size, sort)).withSelfRel());
        if (pageCategories.getTotalPages() > 1) {
            resources.add(linkTo(methodOn(getClass()).findByParentId(categoryId, 1, size, sort))
                    .withRel("first"));
            resources.add(linkTo(methodOn(getClass()).findByParentId(categoryId, pageCategories.getTotalPages(),
                    size, sort)).withRel("last"));
        }
        if ((tempPage + 1) > 1 && (tempPage + 1) <= pageCategories.getTotalPages() && !pageCategories.isFirst()) {
            resources.add(linkTo(methodOn(getClass()).findByParentId(categoryId, (tempPage + 1) - 1, size, sort))
                    .withRel("prev"));
        }
        if (!pageCategories.isLast()) {
            resources.add(linkTo(methodOn(getClass()).findByParentId(categoryId, (tempPage + 1) + 1, size, sort))
                    .withRel("next"));
        }
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @GetMapping(value = "/search/findByAncestor", produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findByAncestor(@RequestParam("categoryId") String categoryId) {
        final List<CategoryDto> categories = new ArrayList<>();
        final Mapper mapper = new Mapper();

        final Resources<CategoryDto> resources;
        categoryService.findByAncestor(categoryId).forEach(category -> {
            final CategoryDto dto = mapper.from(category).toInstanceOf(CategoryDto.class);

            dto.add(linkTo(methodOn(getClass()).findById(dto.getUid())).withSelfRel());
            categories.add(dto);
        });
        resources = new Resources<>(categories);
        resources.add(linkTo(methodOn(getClass()).findByAncestor(categoryId)).withSelfRel());
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
}
