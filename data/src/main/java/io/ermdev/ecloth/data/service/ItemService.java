package io.ermdev.ecloth.data.service;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.exception.UnsatisfiedEntityException;
import io.ermdev.ecloth.data.mapper.CategoryRepository;
import io.ermdev.ecloth.data.mapper.ImageRepository;
import io.ermdev.ecloth.data.mapper.ItemRepository;
import io.ermdev.ecloth.data.mapper.TagRepository;
import io.ermdev.ecloth.model.entity.Category;
import io.ermdev.ecloth.model.entity.Item;
import io.ermdev.ecloth.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository, TagRepository
            tagRepository, ImageRepository imageRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.imageRepository = imageRepository;
    }

    public Item findById(Long itemId) throws EntityNotFoundException {
        final Item item = itemRepository.findById(itemId);
        if(item == null)
            throw new EntityNotFoundException("No item found with id " + itemId);

        final Category category = categoryRepository.findByItemId(itemId);
        final List<Tag> tags = tagRepository.findByItemId(itemId);
        final List<String> images = imageRepository.findByItemId(itemId);

        item.setCategory(category);
        if(tags != null && tags.size() > 0)
            item.getTags().addAll(tags);
        if(images != null && images.size() > 0)
            item.getImages().addAll(images);
        else
            item.getImages().add("/images/item/no_image.png");

        return item;
    }

    public List<Item> findByCategory(Long categoryId) throws EntityNotFoundException {
        final List<Item> items = itemRepository.findByCategory(categoryId);
        if(items == null)
            throw new EntityNotFoundException("No item found");

        items.forEach(item -> {
            final Category category = categoryRepository.findByItemId(item.getId());
            final List<Tag> tags = tagRepository.findByItemId(item.getId());
            final List<String> images = imageRepository.findByItemId(item.getId());

            item.setCategory(category);
            if(tags != null && tags.size() > 0)
                item.getTags().addAll(tags);
            if(images != null && images.size() > 0)
                item.getImages().addAll(images);
            else
                item.getImages().add("/images/item/no_image.png");
        });
        return items;
    }

    public List<Item> findByName(String name) throws EntityNotFoundException {
        final List<Item> items = itemRepository.findByName("%" + name + "%");
        if(items == null)
            throw new EntityNotFoundException("No item found");

        items.forEach(item -> {
            final Category category = categoryRepository.findByItemId(item.getId());
            final List<Tag> tags = tagRepository.findByItemId(item.getId());
            final List<String> images = imageRepository.findByItemId(item.getId());

            item.setCategory(category);
            if(tags != null && tags.size() > 0)
                item.getTags().addAll(tags);
            if(images != null && images.size() > 0)
                item.getImages().addAll(images);
            else
                item.getImages().add("/images/item/no_image.png");
        });
        return items;
    }

    public List<Item> findByName(String name, Long offset, Long size) throws EntityNotFoundException {
        final List<Item> items = itemRepository
                .findByNameFilter(name, offset< 1 ? 0 : (long) offset-1, size < 1 ? itemRepository.countAll() : (long) size);
        if(items == null)
            throw new EntityNotFoundException("No item found");

        items.forEach(item -> {
            final Category category = categoryRepository.findByItemId(item.getId());
            final List<Tag> tags = tagRepository.findByItemId(item.getId());
            final List<String> images = imageRepository.findByItemId(item.getId());

            item.setCategory(category);
            if(tags != null && tags.size() > 0)
                item.getTags().addAll(tags);
            if(images != null && images.size() > 0)
                item.getImages().addAll(images);
            else
                item.getImages().add("/images/item/no_image.png");
        });
        return items;
    }

    public List<Item> findAll() throws EntityNotFoundException {
        final List<Item> items = itemRepository.findAll();
        if(items == null)
            throw new EntityNotFoundException("No item found");
        items.forEach(item -> {
            final Category category = categoryRepository.findByItemId(item.getId());
            final List<Tag> tags = tagRepository.findByItemId(item.getId());
            final List<String> images = imageRepository.findByItemId(item.getId());

            item.setCategory(category);
            if(tags != null && tags.size() > 0)
                item.getTags().addAll(tags);
            if(images != null && images.size() > 0)
                item.getImages().addAll(images);
            else
                item.getImages().add("/images/item/no_image.png");
        });
        return items;
    }

    public List<Item> findAll(Integer offset, Integer size) throws EntityNotFoundException {
        final List<Item> items = itemRepository
                .findAllFilter(offset< 1 ? 0 : (long) offset-1, size < 1 ? itemRepository.countAll() : (long) size);
        if(items == null)
            throw new EntityNotFoundException("No item found");
        items.forEach(item -> {
            final Category category = categoryRepository.findByItemId(item.getId());
            final List<Tag> tags = tagRepository.findByItemId(item.getId());
            final List<String> images = imageRepository.findByItemId(item.getId());

            item.setCategory(category);
            if(tags != null && tags.size() > 0)
                item.getTags().addAll(tags);
            if(images != null && images.size() > 0)
                item.getImages().addAll(images);
            else
                item.getImages().add("/images/item/no_image.png");
        });
        return items;
    }

    public Item add(Item item, Long categoryId) throws EntityNotFoundException, UnsatisfiedEntityException {
        if(item == null)
            throw new UnsatisfiedEntityException("Item is null");
        if(item.getName() == null || item.getName().equals(""))
            throw new UnsatisfiedEntityException("Name is required");
        if(item.getDescription() == null || item.getDescription().equals(""))
            throw new UnsatisfiedEntityException("Description is required");
        if(item.getPrice() == null || item.getPrice() < 0)
            throw new UnsatisfiedEntityException("Price is required");
        if(item.getDiscount() == null || item.getDiscount() < 0)
            throw new UnsatisfiedEntityException("Discount is required");
        if(categoryId == null)
            throw new UnsatisfiedEntityException("Category is required");

        final Category category = categoryRepository.findById(categoryId);
        if(category == null)
            throw new EntityNotFoundException("No category found with id " + categoryId);
        itemRepository.add(item.getName(), item.getDescription(), item.getPrice(), item.getDiscount(), categoryId);

        item.setCategory(category);
        return item;
    }

    public Item updateById(Long itemId, Item item, Long categoryId) throws EntityNotFoundException {
        final Item oldItem = itemRepository.findById(itemId);
        if(item == null)
            return oldItem;
        item.setId(itemId);
        if(item.getName() == null || item.getName().trim().equals(""))
            item.setName(oldItem.getName());
        if(item.getDescription() == null || item.getDescription().trim().equals(""))
            item.setDescription(oldItem.getDescription());
        if(item.getPrice() == null || item.getPrice() == 0)
            item.setPrice(oldItem.getPrice());
        if(item.getDiscount() == null || item.getDiscount() == 0)
            item.setDiscount(oldItem.getDiscount());
        if(categoryId != null) {
            final Category category = categoryRepository.findById(categoryId);
            item.setCategory(category);
        }
        itemRepository.updateById(item);
        return item;
    }

    public Item deleteById(Long itemId) throws EntityNotFoundException {
        final Item item = itemRepository.findById(itemId);
        itemRepository.deleteById(itemId);
        return item;
    }

    public Long countAll() {
        return itemRepository.countAll();
    }

    public Long countByName(String name) {
        return itemRepository.countByName(name);
    }
}
