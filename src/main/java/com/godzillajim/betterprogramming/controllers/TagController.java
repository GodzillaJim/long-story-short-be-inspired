package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import com.godzillajim.betterprogramming.domain.mappers.SearchRequest;
import com.godzillajim.betterprogramming.domain.mappers.TagBody;
import com.godzillajim.betterprogramming.repositories.TagRepository;
import com.godzillajim.betterprogramming.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/tag")
@RequiredArgsConstructor
public class TagController {
    final TagRepository tagRepository;
    final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagBody>> getAllTags(){
        return ResponseEntity.ok(tagService.getAllTags());
    }
    @GetMapping("/{tagId}")
    public ResponseEntity<Tag> getTagDetails(@PathVariable Long tagId){
        return ResponseEntity.ok(tagService.getTagById(tagId));
    }
    @PostMapping("")
    public ResponseEntity<TagBody> createTag(@RequestBody @Valid TagBody tagBody){
        return ResponseEntity.ok(tagService.createTag(tagBody));
    }
    @PutMapping("/{tagId}")
    public ResponseEntity<TagBody> updateTag(@PathVariable Long tagId, @RequestBody @Valid TagBody tagBody){
        return ResponseEntity.ok(tagService.updateTag(tagId, tagBody));
    }
    @DeleteMapping("/{tagId}")
    public ResponseEntity<TagBody> deleteTag(@PathVariable Long tagId){
        return ResponseEntity.ok(tagService.deleteTag(tagId));
    }
    @PostMapping("/search")
    public ResponseEntity<List<TagBody>> searchTags(@RequestBody @Valid SearchRequest request){
        return ResponseEntity.ok(tagService.searchTag(request.getQuery()));
    }
}
