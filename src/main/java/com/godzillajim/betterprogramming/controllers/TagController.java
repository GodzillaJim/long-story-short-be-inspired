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

@io.swagger.v3.oas.annotations.tags.Tag(name = "Public Tags Controller")
@RestController
@RequestMapping("/api/v1/public/tag")
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
    @PostMapping("/search")
    public ResponseEntity<List<TagBody>> searchTags(@RequestBody @Valid SearchRequest request){
        return ResponseEntity.ok(tagService.searchTag(request.getQuery()));
    }
}
