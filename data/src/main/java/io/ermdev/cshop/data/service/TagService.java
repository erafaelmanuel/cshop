package io.ermdev.cshop.data.service;

import io.ermdev.cshop.commons.util.IdGenerator;
import io.ermdev.cshop.data.exception.EntityNotFoundException;
import io.ermdev.cshop.data.exception.UnsatisfiedEntityException;
import io.ermdev.cshop.data.mapper.TagRepository;
import io.ermdev.cshop.model.entity.Tag;
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

    public Tag findById(Long tagId) throws EntityNotFoundException {
        final Tag tag = tagRepository.findById(tagId);
        if(tag == null)
            throw new EntityNotFoundException("No tag found with id " + tagId);
        return tag;
    }

    public List<Tag> findAll() throws EntityNotFoundException {
        List<Tag> tags = tagRepository.findAll();
        if(tags == null)
            throw new EntityNotFoundException("No tag found");
        return tags;
    }

    public List<Tag> findRelatedTag(Long tagId) throws EntityNotFoundException {
        List<Tag> tags = tagRepository.findRelatedTag(tagId);
        if(tags == null)
            throw new EntityNotFoundException("No tag found");
        return tags;
    }

    public Tag add(Tag tag)throws EntityNotFoundException, UnsatisfiedEntityException {
        final long id = IdGenerator.randomUUID();
        if(tag.getTitle()==null || tag.getTitle().trim().equals(""))
            throw new UnsatisfiedEntityException("Title is required");
        if(tag.getDescription()==null || tag.getDescription().trim().equals(""))
            throw new UnsatisfiedEntityException("Description is required");
        if(tag.getKeyword()==null || tag.getKeyword().trim().equals(""))
            throw new UnsatisfiedEntityException("Keyword is required");
        tag.setId(id);
        tagRepository.add(tag);
        return tag;
    }

    public Tag addRelatedTag(Long tagId, Long relatedTagId) throws EntityNotFoundException, UnsatisfiedEntityException {
        final Tag tag = findById(tagId);
        if(relatedTagId == null)
            throw new UnsatisfiedEntityException("Related Id is required");

        final Tag relatedTag = findById(relatedTagId);
        tagRepository.addRelatedTag(tag.getId(), relatedTag.getId());
        return tag;
    }

    public Tag updateById(Long tagId, Tag tag) throws EntityNotFoundException {
        Tag oldTag = findById(tagId);
        if(tag == null)
            return oldTag;
        tag.setId(tagId);
        if(tag.getTitle() == null || tag.getTitle().trim().equals(""))
            tag.setTitle(oldTag.getTitle());
        if(tag.getDescription() == null || tag.getDescription().trim().equals(""))
            tag.setDescription(oldTag.getDescription());
        if(tag.getKeyword() == null || tag.getKeyword().trim().equals(""))
            tag.setKeyword(oldTag.getKeyword());
        tagRepository.updateById(tag);

        return tag;
    }

    public Tag deleteById(Long tagId) throws EntityNotFoundException {
        Tag tag = tagRepository.findById(tagId);
        tagRepository.deleteById(tagId);
        return tag;
    }

    public Tag deleteRelatedTag(Long tagId, Long relatedTagId) throws EntityNotFoundException, UnsatisfiedEntityException {
        final Tag tag = tagRepository.findById(tagId);
        if(relatedTagId == null)
            throw new UnsatisfiedEntityException("Related Id is required");
        final Tag relatedTag = findById(relatedTagId);

        tagRepository.deleteRelatedTag(tag.getId(), relatedTag.getId());
        return tag;
    }
}
