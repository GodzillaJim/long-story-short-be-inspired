package com.godzillajim.betterprogramming.controllers;

import com.godzillajim.betterprogramming.domain.mappers.CategoryBody;
import com.godzillajim.betterprogramming.domain.mappers.SearchRequest;
import com.godzillajim.betterprogramming.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Public Category Controller")
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
    @PostMapping("/search")
    public ResponseEntity<List<CategoryBody>> searchCategory(@RequestBody SearchRequest request){
        return ResponseEntity.ok(categoryService.searchCategories(request.getQuery()));
    }

}
