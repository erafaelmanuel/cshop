package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.IdGenerator;
import io.ermdev.cshop.data.entity.Tag;
import io.ermdev.cshop.data.repository.TagRepository;
import io.ermdev.cshop.exception.EntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findById(Long tagId) throws EntityException {
        final Tag tag = tagRepository.findById(tagId);
        if (tag != null) {
            return tag;
        } else {
            throw new EntityException("No tag found");
        }

    }

    public List<Tag> findAll() throws EntityException {
        List<Tag> tags = tagRepository.findAll();
        if (tags != null) {
            return tags;
        } else {
            throw new EntityException("No tag found");
        }
    }

    public Tag save(Tag tag) throws EntityException {
        if (tag != null) {
            if (tag.getId() == null) {
                if (tag.getName() == null || tag.getName().trim().isEmpty()) {
                    throw new EntityException("Name is required");
                }
                if (tag.getDescription() == null || tag.getDescription().trim().isEmpty()) {
                    throw new EntityException("Description is required");
                }
                final Long generatedId = IdGenerator.randomUUID();
                tag.setId(generatedId);
                tagRepository.add(tag);
                return tag;
            } else {
                final Tag o = tagRepository.findById(tag.getId());
                if (o != null) {
                    if (tag.getName() == null && tag.getName().trim().isEmpty()) {
                        tag.setName(o.getName());
                    }
                    if (tag.getDescription() == null && tag.getDescription().trim().isEmpty()) {
                        tag.setDescription(o.getDescription());
                    }
                    tagRepository.update(tag);
                    return tag;
                } else {
                    tag.setId(null);
                    return save(tag);
                }
            }
        } else {
            throw new NullPointerException("Tag is null");
        }
    }
}
