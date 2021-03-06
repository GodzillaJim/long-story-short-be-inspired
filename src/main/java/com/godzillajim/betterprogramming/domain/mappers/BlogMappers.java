package com.godzillajim.betterprogramming.domain.mappers;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlogMappers {
    public static Blog mapBlogBodyToBlog(BlogBody body, Category category){
        Blog blog = new Blog();
        blog.setPrompt(body.getPrompt());
        blog.setContent(body.getContent());
        blog.setTitle(body.getTitle());
        blog.setSummary(body.getSummary());
        blog.setCategory(category);
        blog.setImg(body.getImg());
        blog.setAuthor(body.getAuthor());
        blog.setAuthor(body.getAuthor());
        blog.setUpdatedBy(body.getUpdatedBy());
        return blog;
    }
    public static BlogBody mapBlogToBlogBody(Blog blog, List<Comment> comments){
        BlogBody body = new BlogBody();
        List<Tag> tags = blog.getTags();
        body.setTitle(blog.getTitle());
        body.setSummary(blog.getSummary());
        body.setPrompt(blog.getPrompt());
        body.setContent(blog.getContent());
        body.setId(blog.getId());
        body.setCreatedOn(blog.getCreatedDate());
        body.setLastModified(blog.getLastModified());
        body.setPublished(blog.isPublished());
        body.setArchived(blog.getArchived());
        body.setImg(blog.getImg());
        body.setAuthor(blog.getAuthor());

        Set<TagBody> tagSet = new HashSet<>();
        tags.forEach((tag)-> tagSet.add(mapTagToTagBody(tag)));
        body.setTags(tagSet);
        List<CommentBody> commentBodies = new ArrayList<>();
        comments.forEach(comment -> commentBodies.add(mapCommentToCommentBody(comment)));
        body.setComments(commentBodies);
        body.setCategory(blog.getCategory().getName());
        body.setViews(blog.getViews());
        return body;
    }
    public static TagBody mapTagToTagBody(Tag tag){
        return new TagBody(tag.getTagName(), tag.getId());
    }
    public static Tag mapTagBodyToTag(TagBody body){
        Tag tag = new Tag();
        tag.setTagName(body.getTag());
        return tag;
    }
    public static Comment mapCommentBodyToComment(CommentBody comment, Blog blog){
        Comment newComment = new Comment();
        newComment.setFirstName(comment.getFirstName());
        newComment.setLastName(comment.getLastName());
        newComment.setContent(comment.getContent());
        newComment.setBlog(blog);
        return newComment;
    }
    public static CommentBody mapCommentToCommentBody(Comment comment){
        CommentBody commentBody = new CommentBody();
        commentBody.setContent(comment.getContent());
        commentBody.setFirstName(comment.getFirstName());
        commentBody.setLastName(comment.getLastName());
        commentBody.setId(comment.getId());
        commentBody.setCreatedOn(comment.getCreatedDate());
        return commentBody;

    }
}
