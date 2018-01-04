package io.ermdev.cshop.data.helper;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.service.ItemService;
import io.ermdev.cshop.data.entity.Category;
import io.ermdev.cshop.data.entity.Item;
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
