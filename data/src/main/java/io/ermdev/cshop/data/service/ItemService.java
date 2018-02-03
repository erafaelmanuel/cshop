package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.IdGenerator;
import io.ermdev.cshop.data.entity.Item;
import io.ermdev.cshop.data.repository.ItemRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findById(Long itemId) throws EntityException {
        final Item item = itemRepository.findById(itemId);
        if (item != null) {
            return item;
        } else {
            throw new EntityException("No item found");
        }
    }

    public List<Item> findAll() throws EntityException {
        final List<Item> items = itemRepository.findAll();
        if (items != null) {
            return items;
        } else {
            throw new EntityException("No item found");
        }
    }

    public Item save(Item item) throws EntityException {
        if (item != null) {
            if (item.getId() == null) {
                if (item.getName() == null || item.getName().trim().isEmpty()) {
                    throw new EntityException("Name is required");
                }
                if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
                    throw new EntityException("Description is required");
                }
                if (item.getPrice() == null) {
                    throw new EntityException("Price is required");
                }
                final long generatedId = IdGenerator.randomUUID();
                item.setId(generatedId);
                itemRepository.add(item);
                return item;
            } else {
                Item o = itemRepository.findById(item.getId());
                if (o != null) {
                    if (item.getName() == null || item.getName().trim().isEmpty()) {
                        item.setName(o.getName());
                    }
                    if (item.getDescription() == null || item.getDescription().trim().isEmpty()) {
                        item.setDescription(o.getDescription());
                    }
                    if (item.getPrice() == null) {
                        item.setPrice(o.getPrice());
                    }
                    itemRepository.update(item);
                    return item;
                } else {
                    item.setId(null);
                    return save(item);
                }
            }
        } else {
            throw new NullPointerException("Item is null");
        }
    }

    public Item delete(Long itemId) throws EntityException {
        final Item item = itemRepository.findById(itemId);
        if (item != null) {
            itemRepository.delete(item);
            return item;
        } else {
            throw new EntityException("No item found");
        }
    }

    public Item delete(Item item) throws EntityException {
        if (item != null && item.getId() != null) {
            final Item o = itemRepository.findById(item.getId());
            if (o != null) {
                itemRepository.delete(item);
                return item;
            } else {
                throw new EntityException("No item found");
            }
        } else {
            throw new NullPointerException("Item is null");
        }
    }
}
