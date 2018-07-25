package com.rem.cs.data.jpa.item;

import com.rem.cs.exception.EntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findById(String id) throws EntityException {
        return itemRepository.findById(id).orElseThrow(() -> new EntityException("No item found"));
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Page<Item> findAll(Specification<Item> specification, Pageable pageable) {
        return itemRepository.findAll(specification, pageable);
    }

    public List<Item> findByCategoryId(String categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }

    public Page<Item> findByCategoryId(String categoryId, Pageable pageable) {
        return itemRepository.findByCategoryId(categoryId, pageable);
    }

    public List<Item> findByCategoryIds(List<String> categoryIds) {
        return itemRepository.findByCategoryIds(categoryIds);
    }

    public Page<Item> findByCategoryIds(List<String> categoryIds, Pageable pageable) {
        return itemRepository.findByCategoryIds(categoryIds, pageable);
    }
}
