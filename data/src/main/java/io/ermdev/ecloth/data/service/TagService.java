package io.ermdev.ecloth.data.service;

import io.ermdev.ecloth.data.exception.EntityNotFoundException;
import io.ermdev.ecloth.data.mapper.TagRepository;
import io.ermdev.ecloth.model.entity.Tag;
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

    public Tag add(Tag tag) {
        tagRepository.add(tag);
        return tag;
    }

    public Tag updateById(Long tagId, Tag tag) throws EntityNotFoundException {
        Tag oldTag = findById(tagId);
        if(tag == null)
            return oldTag;
        tag.setId(tagId);
        if(tag.getName() == null || tag.getName().trim().equals(""))
            tag.setName(oldTag.getName());
        if(tag.getValue() == null || tag.getValue().trim().equals(""))
            tag.setValue(oldTag.getValue());
        tagRepository.updateById(tag);

        return tag;
    }

    public Tag deleteById(Long tagId) throws EntityNotFoundException {
        Tag tag = tagRepository.findById(tagId);
        tagRepository.deleteById(tagId);
        return tag;
    }
}
