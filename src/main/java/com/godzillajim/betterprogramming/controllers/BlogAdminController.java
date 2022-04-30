package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.users.User;
import com.godzillajim.betterprogramming.domain.mappers.BlogBody;
import com.godzillajim.betterprogramming.services.BlogService;
import com.godzillajim.betterprogramming.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Tag(name="Admin Blog Controller")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/v1/admin/blog")
@RequiredArgsConstructor
public class BlogAdminController {
    final BlogService blogService;
    final UserService userService;
    @PostMapping("")
    public Blog createBlog(@RequestBody @Valid BlogBody body, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        body.setAuthor(user);
        body.setUpdatedBy(user);
        return blogService.createBlog(body);
    }
    @PutMapping("/{id}")
    public Blog updateBlog(@PathVariable Long id, @RequestBody @Valid BlogBody body, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        body.setUpdatedBy(user);
        return blogService.updateBlog(id, body);
    }
    @DeleteMapping("/{blogId}/tag/{tagId}")
    public boolean removeBlogTag(@PathVariable Long blogId, @PathVariable Long tagId, Principal principal){
        return blogService.removeTag(blogId, tagId, userService.getUserByUsername(
                principal.getName()));
    }
    @GetMapping("/{blogId}/publish")
    public boolean publishBlog(@PathVariable Long blogId, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        return blogService.publishBlog(blogId, user);
    }
    @GetMapping("/{blogId}/unpublish")
    public boolean unPublishBlog(@PathVariable Long blogId, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        return blogService.unPublishBlog(blogId, user);
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
    public boolean archiveBlog(@PathVariable Long blogId, Principal principal){
        return blogService.archiveBlog(blogId, userService.getUserByUsername(
                principal.getName()));
    }
    @GetMapping("/{blogId}/unarchive")
    public boolean unArchiveBlog(@PathVariable Long blogId, Principal principal){
        return blogService.unArchiveBlog(blogId, userService.getUserByUsername(
                principal.getName()));
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
