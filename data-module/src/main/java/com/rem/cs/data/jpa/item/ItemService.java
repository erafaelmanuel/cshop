package com.rem.cs.data.jpa.item;

import com.rem.cs.exception.EntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findById(String id) throws EntityException{
        return itemRepository.findById(id).orElseThrow(() -> new EntityException("No item found"));
    }

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }
}
