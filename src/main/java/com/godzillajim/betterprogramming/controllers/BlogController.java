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
@RequestMapping("/api/v1/public/blog")
public class BlogController {
    private final BlogService blogService;
    @GetMapping("")
    public List<BlogBody> getAllBlogs(){
        return blogService.getAllBlogs();
    }
    @GetMapping("/{id}")
    public BlogBody getBlogDetails(@PathVariable Long id){
        return blogService.getBlogDetails(id);
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
    @PostMapping("/{blogId}/tag")
    public boolean addBlogTags(@PathVariable Long blogId, @RequestBody @Valid TagBody tagBody){
        return blogService.addTag(blogId, tagBody);
    }
    @PostMapping("/bulk")
    public boolean addBBulkBlogs(@RequestBody @Valid List<BlogBody> blogs){
        return blogService.bulkAddBlogs(blogs);
    }
}
