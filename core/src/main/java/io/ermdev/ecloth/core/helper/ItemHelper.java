package io.ermdev.ecloth.core.helper;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.ItemService;
import io.ermdev.ecloth.model.entity.Category;
import io.ermdev.ecloth.model.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemHelper {

    private ItemService itemService;

    public ItemHelper(ItemService itemService) {
        this.itemService = itemService;
    }

    public List<Item> searchFromCategories(List<Category> categories) {
        List<Item> items = new ArrayList<>();
        categories.parallelStream().forEach(category -> {
            try {
                items.addAll(itemService.findByCategory(category.getId()));
            } catch (EntityNotFoundException e) {
                e.printStackTrace();
            }
        });
        return items;
    }


}
