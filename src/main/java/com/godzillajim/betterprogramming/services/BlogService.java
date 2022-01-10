package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.mappers.BlogBody;
import com.godzillajim.betterprogramming.domain.mappers.BlogMappers;
import com.godzillajim.betterprogramming.domain.mappers.CommentBody;
import com.godzillajim.betterprogramming.repositories.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CommentService commentService;
    private final TagService tagService;
    public List<BlogBody> getAllBlogs(){
        List<BlogBody> bodies = new ArrayList<>();
        List<Blog> blogs = blogRepository.findAll();
        blogs.forEach((blog)-> {
            List<Tag> tags = tagService.getTagByBlogs(blog);
            bodies.add(BlogMappers.mapBlogToBlogBody(blog,tags));
        });
        return bodies;
    }
    public BlogBody getBlogDetails(Long id){
        Blog blog = blogRepository.findBlogById(id).orElse(null);
        if(blog != null){
            BlogBody blogBody = new BlogBody();
            List<Tag> tags = tagService.getTagByBlogs(blog);
            blogBody = BlogMappers.mapBlogToBlogBody(blog,tags);
            return blogBody;
        }
        return new BlogBody();
    }
    public Blog updateBlog(Long id, BlogBody blogBody){
        Blog blog = blogRepository.findBlogById(id).orElse(null);
        String title = blogBody.getTitle();
        String content = blogBody.getContent();
        String summary = blogBody.getSummary();
        String prompt = blogBody.getPrompt();
        if(blog != null){
            blog.setTitle(title);
            blog.setContent(content);
            blog.setSummary(summary);
            blog.setPrompt(prompt);
            return blogRepository.save(blog);
        }
        return null;
    }
    public Blog createBlog(BlogBody body){
        Blog blog  = new Blog();
        blog.setTitle(body.getTitle());
        blog.setContent(blog.getContent());
        Blog createdBlog = blogRepository.save(blog);
        body.getTags().forEach((t)-> {
            tagService.AddTag(createdBlog, t);
        });
        return createdBlog;
    }
    public Boolean deleteBlog(Long id){
        Blog blog = blogRepository.findBlogById(id).orElse(null);
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
        Blog blog = blogRepository.findBlogById(blogId).orElse(null);
        Comment comment1 = commentService.addNewComment(comment);
        if(blog != null){
            blog.getComments().add(comment1);
            blogRepository.save(blog);
            return true;
        }
        return false;
    }
    public boolean removeComment(Long blogId, Long commentId){
        Blog blog = blogRepository.findBlogById(blogId).orElse(null);
        if(blog != null){
            Comment comment = commentService.getCommentDetails(commentId);
            blog.getComments().remove(comment);
            blogRepository.save(blog);
            return commentService.deleteComment(commentId);
        }
        return false;
    }
}
