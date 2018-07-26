package com.rem.cs.rest.client.item;

import org.springframework.web.client.RestTemplate;

public class ItemService {

    private RestTemplate restTemplate;

    public ItemService() {
        restTemplate = new RestTemplate();
    }

    public Item getById(String id) {
        return restTemplate.getForObject("http://localhost:8080/api/items/" + id, Item.class);
    }
}
