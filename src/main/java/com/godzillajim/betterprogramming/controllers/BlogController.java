package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.mappers.BlogBody;
import com.godzillajim.betterprogramming.domain.mappers.CommentBody;
import com.godzillajim.betterprogramming.services.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/blog")
public class BlogController {
    private final BlogService blogService;
    @GetMapping("")
    public List<Blog> getAllBlogs(){
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
    public Blog getBlogDetails(@PathVariable Long id){
        return blogService.getBlogDetails(id);
    }
    @PostMapping("")
    public Blog createBlog(@RequestBody BlogBody body){
        return blogService.createBlog(body);
    }
    //TODO: Update blog
    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody Map<String, String> body){
        String title = body.get("title");
        String content = body.get("content");
        return blogService.updateBlog(id, title, content);
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
}
