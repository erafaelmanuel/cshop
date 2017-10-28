package io.ermdev.ecloth.data.service;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.mapper.AttributeRepository;
import io.ermdev.ecloth.model.entity.Attribute;
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

    public Attribute add(Attribute attribute) {
        if(attribute == null)
            return null;
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
