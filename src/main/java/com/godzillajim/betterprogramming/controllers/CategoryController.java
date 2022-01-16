package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.mappers.CategoryBody;
import com.godzillajim.betterprogramming.domain.mappers.SearchRequest;
import com.godzillajim.betterprogramming.domain.payloads.CategoryRequest;
import com.godzillajim.betterprogramming.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public/category")
@RequiredArgsConstructor
public class CategoryController {
    final CategoryService categoryService;
    @GetMapping
    public ResponseEntity<List<CategoryBody>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryBody> getCategoryDetails(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.getCategoryById(categoryId));
    }
    @PostMapping
    public ResponseEntity<CategoryBody> addCategory(@RequestBody @Valid CategoryRequest request){
        return ResponseEntity.ok(categoryService.createCategory(request));
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryBody> updateCategory(@RequestBody @Valid CategoryBody categoryBody, @PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(categoryBody, categoryId));
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    }
    @PostMapping("/search")
    public ResponseEntity<List<CategoryBody>> searchCategory(@RequestBody SearchRequest request){
        return ResponseEntity.ok(categoryService.searchCategories(request.getQuery()));
    }
    @GetMapping("/{id}/archive")
    public ResponseEntity<CategoryBody> archiveCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.archiveCategory(id));
    }
    @GetMapping("/{id}/unarchive")
    public ResponseEntity<CategoryBody> unArchiveCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.unArchiveCategory(id));
    }
}
