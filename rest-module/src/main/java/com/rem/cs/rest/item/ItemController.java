package com.rem.cs.rest.item;

import com.rem.cs.data.jpa.item.ItemService;
import com.rem.mappyfy.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class PussyController {

    private ItemService itemService;

    @Autowired
    public PussyController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/puta")
    public ResponseEntity<?> getAll() {
        try {
            final Mapper mapper = new Mapper();

            return new ResponseEntity<>(mapper
                    .from(itemService.findAll())
                    .ignore("categories")
                    .toListOf(ItemDto.class), HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
