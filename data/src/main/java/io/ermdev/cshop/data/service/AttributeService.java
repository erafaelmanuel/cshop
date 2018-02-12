package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.IdGenerator;
import io.ermdev.cshop.data.entity.Attribute;
import io.ermdev.cshop.data.repository.AttributeRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeService {

    private AttributeRepository attributeRepository;

    public AttributeService(AttributeRepository attributeRepository) {
        this.attributeRepository = attributeRepository;
    }

    public Attribute findById(Long attributeId) throws EntityException {
        Attribute attribute = attributeRepository.findById(attributeId);
        if (attribute != null) {
            return attribute;
        } else {
            throw new EntityException("No attribute found");
        }
    }

    public List<Attribute> findAll() throws EntityException {
        List<Attribute> attributes = attributeRepository.findAll();
        if (attributes != null) {
            return attributes;
        } else {
            throw new EntityException("No attribute found");
        }
    }

    public Attribute save(Attribute attribute) throws EntityException {
        if (attribute != null) {
            if (attribute.getId() == null) {
                if (attribute.getName() == null || attribute.getName().trim().isEmpty()) {
                    throw new EntityException("Name is required");
                }
                if (attribute.getType() == null || attribute.getType().trim().isEmpty()) {
                    throw new EntityException("Type is required");
                }
                final Long generatedId = IdGenerator.randomUUID();
                attribute.setId(generatedId);
                attributeRepository.add(attribute);
                return attribute;
            } else {
                final Attribute o = attributeRepository.findById(attribute.getId());
                if (o != null) {
                    if (attribute.getName() == null && attribute.getName().trim().isEmpty()) {
                        attribute.setName(o.getName());
                    }
                    if (attribute.getType() == null && attribute.getType().trim().isEmpty()) {
                        attribute.setType(o.getType());
                    }
                    attributeRepository.update(attribute);
                    return attribute;
                } else {
                    attribute.setId(null);
                    return save(attribute);
                }
            }
        } else {
            throw new NullPointerException("Attribute is null");
        }
    }

    public Attribute delete(Long attributeId) throws EntityException {
        final Attribute attribute = attributeRepository.findById(attributeId);
        if (attribute != null) {
            attributeRepository.delete(attribute);
            return attribute;
        } else {
            throw new EntityException("No attribute found");
        }
    }

    public Attribute delete(Attribute attribute) throws EntityException {
        if (attribute != null) {
            final Attribute o = attributeRepository.findById(attribute.getId());
            if (o != null) {
                attributeRepository.delete(o);
                return o;
            } else {
                throw new EntityException("No attribute found");
            }
        } else {
            throw new NullPointerException("Attribute is null");
        }
    }
}
