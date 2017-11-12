package io.ermdev.cshop.data.service;

import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.exception.UnsatisfiedEntityException;
import io.ermdev.cshop.data.mapper.AttributeRepository;
import io.ermdev.cshop.model.entity.Attribute;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService {

    private AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public Attribute findById(Long attributeId) throws EntityNotFoundException {
        Attribute attribute = attributeRepository.findById(attributeId);
        if(attribute == null)
            throw new EntityNotFoundException("No attribute found with id " + attributeId);
        return attribute;
    }

    public List<Attribute> findAll() throws EntityNotFoundException {
        List<Attribute> attributes = attributeRepository.findAll();
        if(attributes == null)
            throw new EntityNotFoundException("No attribute found");
        return attributes;
    }

    public Attribute add(Attribute attribute) throws UnsatisfiedEntityException {
        if(attribute == null)
            throw new UnsatisfiedEntityException("attribute is null");
        if(attribute.getTitle() == null || attribute.getTitle().trim().equals(""))
            throw new UnsatisfiedEntityException("Title is required");
        if(attribute.getContent() == null || attribute.getContent().trim().equals(""))
            throw new UnsatisfiedEntityException("Content is required");
        if(attribute.getDescription() == null || attribute.getDescription().trim().equals(""))
            throw new UnsatisfiedEntityException("Description is required");
        if(attribute.getType() == null || attribute.getType().trim().equals(""))
            throw new UnsatisfiedEntityException("Type is required");
        attributeRepository.add(attribute);
        return attribute;
    }

    public Attribute updateById(Long attributeId, Attribute attribute) throws EntityNotFoundException {
        Attribute oldAttribute = findById(attributeId);
        if(attribute == null)
            return oldAttribute;
        attribute.setId(attributeId);
        if(attribute.getTitle() == null || attribute.getTitle().trim().equals(""))
            attribute.setTitle(oldAttribute.getTitle());
        if(attribute.getContent() == null || attribute.getContent().trim().equals(""))
            attribute.setContent(oldAttribute.getContent());
        if(attribute.getDescription() == null || attribute.getDescription().trim().equals(""))
            attribute.setDescription(oldAttribute.getDescription());
        if(attribute.getType() == null || attribute.getType().trim().equals(""))
            attribute.setType(oldAttribute.getType());
        return attribute;
    }

    public Attribute deleteById(Long attributeId) throws EntityNotFoundException {
        Attribute attribute = attributeRepository.findById(attributeId);
        attributeRepository.deleteById(attributeId);

        return attribute;
    }
}
