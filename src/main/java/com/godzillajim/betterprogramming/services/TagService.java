package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.mappers.BlogMappers;
import com.godzillajim.betterprogramming.domain.mappers.TagBody;
import com.godzillajim.betterprogramming.errors.BadRequestException;
import com.godzillajim.betterprogramming.errors.ResourceNotFoundException;
import com.godzillajim.betterprogramming.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    public void AddTag(Blog blog, TagBody tagBody){
        Tag testTag = tagRepository.findTagByTagName(tagBody.getTag()).orElse(null);
        if(testTag == null){
            Tag tag = BlogMappers.mapTagBodyToTag(tagBody);
            tag.setBlog(blog);
            tagRepository.save(tag);
            return;
        }
    }
    public Tag getTagDetails(String tagName){
        return tagRepository.findTagByTagName(tagName).orElseThrow(()-> new ResourceNotFoundException(String.format("Tag {%s} does not exist", tagName)));
    }
    public List<Tag> getTagByBlogs(Blog blog){
        return tagRepository.findTagByBlog(blog);
    }

    public void removeTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(()-> new ResourceNotFoundException(String.format("Tag {%s} does not exist", tagId)));
        tagRepository.delete(tag);
    }
}
