package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.entity.Image;
import io.ermdev.cshop.data.repository.ImageItemRepository;
import io.ermdev.cshop.data.repository.ImageRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private ImageRepository imageRepository;
    private ImageItemRepository imageItemRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, ImageItemRepository imageItemRepository) {
        this.imageRepository = imageRepository;
        this.imageItemRepository = imageItemRepository;
    }

    public Image findById(Long imageId) throws EntityException {
        Image image = imageRepository.findById(imageId);
        if(image != null) {
            return image;
        } else {
            throw new EntityException("No image found");
        }
    }

    public List<Image> findAll() throws EntityException {
        List<Image> images = imageRepository.findAll();
        if(images != null) {
            return images;
        } else {
            throw new EntityException("No image found");
        }
    }

    public List<Image> findByItemId(Long itemId) throws EntityException {
        List<Image> images = imageItemRepository.findImagesByItemId(itemId);
        if(images != null) {
            return images;
        } else {
            throw new EntityException("No image found");
        }
    }
}
