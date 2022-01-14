package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.mappers.BlogBody;
import com.godzillajim.betterprogramming.domain.mappers.BlogMappers;
import com.godzillajim.betterprogramming.domain.mappers.CommentBody;
import com.godzillajim.betterprogramming.domain.mappers.TagBody;
import com.godzillajim.betterprogramming.errors.BadRequestException;
import com.godzillajim.betterprogramming.errors.ResourceNotFoundException;
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
            List<Comment> comments = commentService.getCommentsByBlog(blog);
            bodies.add(BlogMappers.mapBlogToBlogBody(blog,tags, comments));
        });
        return bodies;
    }
    public BlogBody getBlogDetails(Long id) throws ResourceNotFoundException {
        Blog blog = blogRepository
                .findBlogById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format("The blog with id %s does not exist.", id.toString())));
        BlogBody blogBody;
        List<Tag> tags = tagService.getTagByBlogs(blog);
        List<Comment> comments = commentService.getCommentsByBlog(blog);
        blogBody = BlogMappers.mapBlogToBlogBody(blog,tags,comments);
        return blogBody;
    }
    public Blog updateBlog(Long id, BlogBody blogBody){
        Blog blog = blogRepository
                .findBlogById(id)
                .orElseThrow(
                        ()-> new ResourceNotFoundException(String.format("The blog with id %s does not exist.", id.toString())));
        String title = blogBody.getTitle();
        String content = blogBody.getContent();
        String summary = blogBody.getSummary();
        String prompt = blogBody.getPrompt();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setSummary(summary);
        blog.setPrompt(prompt);
        return blogRepository.save(blog);
    }
    public Blog createBlog(BlogBody body){
        Blog testBlog = blogRepository.findBlogByTitle(body.getTitle()).orElse(null);
        if(testBlog != null){
            throw new BadRequestException(String.format("This title already exists: {%s}", body.getTitle()));
        }
        Blog blog = BlogMappers.mapBlogBodyToBlog(body);
        Blog createdBlog = blogRepository.save(blog);
        body.getTags().forEach((t)-> tagService.AddTag(createdBlog, t));
        return createdBlog;
    }
    public Boolean deleteBlog(Long id){
        Blog blog = blogRepository.findBlogById(id).orElseThrow(()-> new ResourceNotFoundException(String.format("The blog with id %s does not exist.", id.toString())));
        blogRepository.delete(blog);
        return true;
    }
    public List<Blog> searchBlogs(String searchTerm){
        return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }
    @Transactional
    public boolean addComment(Long blogId, CommentBody comment){
        Blog blog = blogRepository
                .findBlogById(blogId)
                .orElseThrow(()->
                new ResourceNotFoundException(
                        String.format("The blog with id %s does not exist.", blogId.toString()
                        )
                )
                );
        commentService.addNewComment(BlogMappers.mapCommentBodyToComment(comment, blog));
        blogRepository.save(blog);
        return true;
    }
    public boolean removeComment(Long blogId, Long commentId){
        Blog blog = blogRepository.findBlogById(blogId).orElseThrow(()-> new ResourceNotFoundException(
                String.format("The blog with id %s does not exist.", blogId.toString())
        ));
        return commentService.deleteComment(commentId);
    }
    public boolean addTag(Long blogId,TagBody tag){
        Blog blog = blogRepository.findBlogById(blogId).orElseThrow(()-> new ResourceNotFoundException(
                String.format("The blog with id %s does not exist.", blogId.toString())
        ));
        tagService.AddTag(blog, tag);
        return true;
    }
    public boolean removeTag(Long blogId, Long tagId){
        Blog blog = blogRepository.findBlogById(blogId).orElseThrow(()-> new ResourceNotFoundException(
                String.format("The blog with id %s does not exist.", blogId.toString())
        ));
        tagService.removeTag(tagId);
        return true;
    }
    public boolean bulkAddBlogs(List<BlogBody> blogs){
        blogs.forEach(blogBody -> {
            //TODO: Create blog
            Blog blog = createBlog(blogBody);
            //TODO: Create comment
            List<CommentBody> comments = blogBody.getComments();
            comments.forEach(commentBody -> {
                Comment newComment = BlogMappers.mapCommentBodyToComment(commentBody, blog);
                commentService.addNewComment(newComment);
            });
        });
        return true;
    }

}
