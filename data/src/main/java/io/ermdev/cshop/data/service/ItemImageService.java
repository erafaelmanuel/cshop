package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.entity.Image;
import io.ermdev.cshop.data.entity.Item;
import io.ermdev.cshop.data.repository.ImageRepository;
import io.ermdev.cshop.data.repository.ItemImageRepository;
import io.ermdev.cshop.data.repository.ItemRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemImageService {

    private ItemRepository itemRepository;
    private ImageRepository imageRepository;
    private ItemImageRepository itemImageRepository;

    @Autowired
    public ItemImageService(ItemRepository itemRepository, ImageRepository imageRepository,
                            ItemImageRepository itemImageRepository) {
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
        this.itemImageRepository = itemImageRepository;
    }

    public List<Image> findImagesByItemId(Long itemId) throws EntityException {
        List<Image> images = itemImageRepository.findImagesByItemId(itemId);
        if (images != null && images.size() > 0) {
            return images;
        } else {
            throw new EntityException("No image found");
        }
    }

    public Image findImageByItemIdAndImageId(Long itemId, Long imageId) throws EntityException {
        Image image = itemImageRepository.findImageByItemIdAndImageId(itemId, imageId);
        if (image != null) {
            return image;
        } else {
            throw new EntityException("No image found");
        }
    }

    public Image addImageToItem(Long itemId, Long imageId) throws EntityException {
        final Item item = itemRepository.findById(itemId);
        final Image image = imageRepository.findById(imageId);
        if (item == null) {
            throw new EntityException("No item found");
        }
        if (image == null) {
            throw new EntityException("No image found");
        }
        itemImageRepository.addImageToItem(itemId, imageId);
        return image;
    }

    public Image deleteImageFromItem(Long itemId, Long imageId) throws EntityException {
        final Item item = itemRepository.findById(itemId);
        final Image image = itemImageRepository.findImageByItemIdAndImageId(itemId, imageId);
        if (item == null) {
            throw new EntityException("No item found");
        }
        if (image == null) {
            throw new EntityException("No image found");
        }
        itemImageRepository.deleteImageFromItem(itemId, imageId);
        return image;
    }
}
