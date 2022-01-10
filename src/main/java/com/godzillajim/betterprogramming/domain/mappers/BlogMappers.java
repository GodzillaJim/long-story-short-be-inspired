package com.godzillajim.betterprogramming.domain.mappers;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlogMappers {
    public static BlogBody mapBlogToBlogBody(Blog blog, List<Tag> tags){
        BlogBody body = new BlogBody();
        body.setTitle(blog.getTitle());
        body.setSummary(blog.getSummary());
        body.setPrompt(blog.getPrompt());
        body.setContent(blog.getContent());
        body.setId(blog.getId());
        body.setCreatedOn(blog.getCreatedDate());
        body.setLastModified(blog.getLastModified());
        body.setPublished(blog.isPublished());
        Set<TagBody> tagSet = new HashSet<>();
        tags.forEach((tag)->{
            tagSet.add(mapTagToTagBody(tag));
        } );
        body.setTags(tagSet);
        return body;
    }
    public static TagBody mapTagToTagBody(Tag tag){
        return new TagBody(tag.getTagName());
    }
    public static Tag mapTagBodyToTag(TagBody body){
        Tag tag = new Tag();
        tag.setTagName(body.getTag());
        return tag;
    }
}
