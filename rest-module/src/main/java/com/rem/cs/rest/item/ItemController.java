package com.rem.cs.rest.item;

import com.rem.cs.data.jpa.item.ItemService;
import com.rem.cs.exception.EntityException;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<?> getAll(Pageable pageable) {
        final Mapper mapper = new Mapper();
        final List<ItemDto> resources = new ArrayList<>();

        itemService.findAll(pageable).forEach(item -> {
            final ItemDto dto = mapper.from(item).toInstanceOf(ItemDto.class);

            dto.add(linkTo(methodOn(getClass()).getById(dto.getUid())).withSelfRel());
            resources.add(dto);
        });
        return new ResponseEntity<>(new Resources<>(resources, linkTo(getClass()).withSelfRel()), HttpStatus.OK);
    }

    @GetMapping(value = {"/{itemId}"}, produces = {"application/json"})
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
