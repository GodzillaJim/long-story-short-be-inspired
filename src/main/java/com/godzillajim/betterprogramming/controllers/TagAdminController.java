package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.mappers.TagBody;
import com.godzillajim.betterprogramming.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/tag")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class TagAdminController {
    private final TagService tagService;
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
    @PostMapping("/bulk")
    public ResponseEntity<Boolean> addBulk(@RequestBody List<TagBody> tagBodyList){
        return ResponseEntity.ok(tagService.addManyTags(tagBodyList));
    }
}
