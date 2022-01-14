package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.mappers.BlogBody;
import com.godzillajim.betterprogramming.domain.mappers.CommentBody;
import com.godzillajim.betterprogramming.domain.mappers.TagBody;
import com.godzillajim.betterprogramming.services.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {
    private final BlogService blogService;
    @GetMapping("")
    public List<BlogBody> getAllBlogs(){
        return blogService.getAllBlogs();
    }
    @DeleteMapping("/{id}")
    public String deleteBlog(@PathVariable Long id){
        boolean deleted = blogService.deleteBlog(id);
        if(deleted){
            return "Blog deleted";
        }
        return "Deletion unsuccessful";
    }
    @GetMapping("/{id}")
    public BlogBody getBlogDetails(@PathVariable Long id){
        return blogService.getBlogDetails(id);
    }
    @PostMapping("")
    public Blog createBlog(@RequestBody @Valid BlogBody body){
        return blogService.createBlog(body);
    }
    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody @Valid BlogBody body){
        return blogService.updateBlog(id, body);
    }
    @PostMapping("/search")
    public List<Blog> searchBlogs(@RequestBody Map<String, String> body){
        String text = body.get("query");
        return blogService.searchBlogs(text);
    }
    @PostMapping("/{blogId}/comment")
    public Boolean addComment(@PathVariable Long blogId, @RequestBody CommentBody comment){
        return blogService.addComment(blogId, comment);
    }
    @DeleteMapping("/{blogId}/comment/{commentId}")
    public Boolean removeComment(@PathVariable Long blogId, @PathVariable Long commentId){
        return blogService.removeComment(blogId, commentId);
    }
    @PostMapping("/{blogId}/tag")
    public boolean addBlogTags(@PathVariable Long blogId, @RequestBody @Valid TagBody tagBody){
        return blogService.addTag(blogId, tagBody);
    }
    @DeleteMapping("/{blogId}/tag/{tagId}")
    public boolean removeBlogTag(@PathVariable Long blogId, @PathVariable Long tagId){
        return blogService.removeTag(blogId, tagId);
    }
    @PostMapping("/bulk")
    public boolean addBBulkBlogs(@RequestBody @Valid List<BlogBody> blogs){
        return blogService.bulkAddBlogs(blogs);
    }
}
