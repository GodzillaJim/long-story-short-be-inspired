package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.mappers.BlogBody;
import com.godzillajim.betterprogramming.services.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/v1/admin/blog")
@RequiredArgsConstructor
public class BlogAdminController {
    final BlogService blogService;
    @PostMapping("")
    public Blog createBlog(@RequestBody @Valid BlogBody body){
        return blogService.createBlog(body);
    }
    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody @Valid BlogBody body){
        return blogService.updateBlog(id, body);
    }
    @DeleteMapping("/{blogId}/tag/{tagId}")
    public boolean removeBlogTag(@PathVariable Long blogId, @PathVariable Long tagId){
        return blogService.removeTag(blogId, tagId);
    }
    @GetMapping("/{blogId}/publish")
    public boolean publishBlog(@PathVariable Long blogId){
        return blogService.publishBlog(blogId);
    }
    @GetMapping("/{blogId}/unpublish")
    public boolean unPublishBlog(@PathVariable Long blogId){
        return blogService.unPublishBlog(blogId);
    }
    @DeleteMapping("/{id}")
    public String deleteBlog(@PathVariable Long id){
        boolean deleted = blogService.deleteBlog(id);
        if(deleted){
            return "Blog deleted";
        }
        return "Deletion unsuccessful";
    }
    @GetMapping("/{blogId}/archive")
    public boolean archiveBlog(@PathVariable Long blogId){
        return blogService.archiveBlog(blogId);
    }
    @GetMapping("/{blogId}/unarchive")
    public boolean unArchiveBlog(@PathVariable Long blogId){
        return blogService.unArchiveBlog(blogId);
    }
    @DeleteMapping("/{blogId}/comment/{commentId}")
    public Boolean removeComment(@PathVariable Long blogId, @PathVariable Long commentId){
        return blogService.removeComment(blogId, commentId);
    }
    @GetMapping("/archived")
    public ResponseEntity<List<Blog>> getArchivedBlogs(){
        return ResponseEntity.ok(blogService.getArchivedBlogs());
    }
}
