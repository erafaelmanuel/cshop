package io.ermdev.ecloth.data.helper;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.service.ItemService;
import io.ermdev.ecloth.model.entity.Category;
import io.ermdev.ecloth.model.entity.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
