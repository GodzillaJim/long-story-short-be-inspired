package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.mappers.BlogBody;
import com.godzillajim.betterprogramming.domain.mappers.CommentBody;
import com.godzillajim.betterprogramming.repositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CommentService commentService;
    private final TagService tagService;
    public List<Blog> getAllBlogs(){
        return blogRepository.findAll();
    }
    public Blog getBlogDetails(Long id){
        return blogRepository.getById(id);
    }
    public Blog updateBlog(Long id, String title, String content){
        Blog blog = getBlogDetails(id);
        if(blog != null){
            blog.setTitle(title);
            blog.setContent(content);
            blogRepository.save(blog);
        }
        return blog;
    }
    public Blog createBlog(BlogBody body){
        Blog blog  = new Blog();
        blog.setTitle(body.getTitle());
        blog.setContent(blog.getContent());
        Set<Tag> tags = new HashSet<>();
        body.getTags().forEach((t)-> { tags.add(tagService.AddTag(t)); });
        blog.setTags(tags);
        return blogRepository.save(blog);
    }
    public Boolean deleteBlog(Long id){
        Blog blog = getBlogDetails(id);
        if(blog != null){
            blogRepository.delete(blog);
            return true;
        }
        return false;
    }
    public List<Blog> searchBlogs(String searchTerm){
        return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }
    @Transactional
    public boolean addComment(Long blogId, CommentBody comment){
        Blog blog = getBlogDetails(blogId);
        Comment comment1 = commentService.addNewComment(comment);
        if(blog != null){
            blog.getComments().add(comment1);
            blogRepository.save(blog);
            return true;
        }
        return false;
    }
    public boolean removeComment(Long blogId, Long commentId){
        Blog blog = getBlogDetails(blogId);
        if(blog != null){
            Comment comment = commentService.getCommentDetails(commentId);
            blog.getComments().remove(comment);
            blogRepository.save(blog);
            return commentService.deleteComment(commentId);
        }
        return false;
    }
}
