package com.rem.cs.rest.controller;

import com.rem.cs.data.jpa.entity.Category;
import com.rem.cs.data.jpa.repository.CategoryRepository;
import com.rem.cs.exception.EntityException;
import com.rem.cs.rest.dto.CategoryDto;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController("categoryRestController")
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryRepository categoryRepo;

    @Autowired
    public CategoryController(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @GetMapping(produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findAll(@RequestParam(name = "search", required = false) String search,
                                     @RequestParam(name = "page", required = false) Integer page,
                                     @RequestParam(name = "size", required = false) Integer size,
                                     @RequestParam(name = "sort", required = false) String sort) {
        final List<CategoryDto> categories = new ArrayList<>();
        final Mapper mapper = new Mapper();

        categoryRepo.findAll().forEach(category -> {
            final CategoryDto dto = mapper.from(category).toInstanceOf(CategoryDto.class);

            categories.add(dto);
        });

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "{categoryId}", produces = {"application/json", "application/hal+json"})
    public ResponseEntity<?> findById(@PathVariable("categoryId") String categoryId) {
        final Mapper mapper = new Mapper();
        final Optional<Category> category = categoryRepo.findById(categoryId);

        try {
            final CategoryDto dto = mapper.from(category.orElseThrow(()->
                    new EntityException("No category found"))).toInstanceOf(CategoryDto.class);
            final Resource<CategoryDto> resource = new Resource<>(dto);

            resource.add(linkTo(methodOn(getClass()).findById(categoryId)).withSelfRel());
            return new ResponseEntity<>(resource, HttpStatus.OK);
        } catch (EntityException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
