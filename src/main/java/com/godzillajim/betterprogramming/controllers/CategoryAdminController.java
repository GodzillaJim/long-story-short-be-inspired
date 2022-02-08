package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import com.godzillajim.betterprogramming.domain.mappers.CategoryBody;
import com.godzillajim.betterprogramming.domain.payloads.CategoryRequest;
import com.godzillajim.betterprogramming.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name="Admin Category Controller")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/api/v1/admin/category")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryService categoryService;

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
    @GetMapping("/{id}/archive")
    public ResponseEntity<CategoryBody> archiveCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.archiveCategory(id));
    }
    @GetMapping("/{id}/unarchive")
    public ResponseEntity<CategoryBody> unArchiveCategory(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.unArchiveCategory(id));
    }
    @PostMapping("/bulk")
    public ResponseEntity<List<Category>> bulkAdd(@RequestBody List<CategoryBody> categoryBodies){
        return ResponseEntity.ok(categoryService.addBulk(categoryBodies));
    }
}
