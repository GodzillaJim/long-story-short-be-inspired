package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.mappers.BlogMappers;
import com.godzillajim.betterprogramming.domain.mappers.TagBody;
import com.godzillajim.betterprogramming.repositories.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;
    public Tag AddTag(Blog blog, TagBody tagBody){
        if(getTagDetails(tagBody.getTag()) == null){
            Tag tag = BlogMappers.mapTagBodyToTag(tagBody);
            tag.setBlog(blog);
            return tagRepository.save(tag);
        }
        return getTagDetails(tagBody.getTag());
    }
    public Tag getTagDetails(String tagName){
        return tagRepository.findTagByTagName(tagName).orElse(null);
    }
    public List<Tag> getTagByBlogs(Blog blog){
        return tagRepository.findTagByBlog(blog);
    }

    public void removeTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).orElse(null);
        if(tag != null){
            tagRepository.delete(tag);
        }
    }
}
