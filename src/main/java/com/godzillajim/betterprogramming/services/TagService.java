package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.mappers.BlogMappers;
import com.godzillajim.betterprogramming.domain.mappers.TagBody;
import com.godzillajim.betterprogramming.errors.ResourceNotFoundException;
import com.godzillajim.betterprogramming.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    public Tag getTagDetails(String tagName){
        return tagRepository.findTagByTagName(tagName).orElseThrow(()-> new ResourceNotFoundException(String.format("Tag {%s} does not exist", tagName)));
    }
    public void removeTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(()-> new ResourceNotFoundException(String.format("Tag {%s} does not exist", tagId)));
        tagRepository.delete(tag);
    }

    public TagBody createTag(TagBody tagBody) {
        Tag tag = tagRepository
                .findTagByTagName(tagBody.getTag())
                .orElse(null);
        if(tag == null){
            tag = BlogMappers.mapTagBodyToTag(tagBody);
            tag = tagRepository.save(tag);
        }
        return BlogMappers.mapTagToTagBody(tag);
    }
    public TagBody updateTag(Long tagId, TagBody tagBody) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(()-> new ResourceNotFoundException(String.format("Tag {%s} does not exist", tagId)));
        tag.setTagName(tagBody.getTag());
        return BlogMappers.mapTagToTagBody(tagRepository.save(tag));
    }

    public TagBody deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(()-> new ResourceNotFoundException(String.format("Tag {%s} does not exist", tagId)));
        tagRepository.delete(tag);
        return BlogMappers.mapTagToTagBody(tag);
    }

    public List<TagBody> searchTag(String query) {
        List<Tag> tags = tagRepository.findTagsByTagNameContains(query);
        List<TagBody> tagBodies = new ArrayList<>();
        tags.forEach(tag -> tagBodies.add(BlogMappers.mapTagToTagBody(tag)));
        return tagBodies;
    }
    public List<TagBody> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        List<TagBody> tagBodies = new ArrayList<>();
        tags.forEach(tag -> tagBodies.add(BlogMappers.mapTagToTagBody(tag)));
        return tagBodies;
    }

    public Tag getTagById(Long tagId) {
        return tagRepository
                .findTagById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Tag does not exist {%s}",tagId)));
    }
}
