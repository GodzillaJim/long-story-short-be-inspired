package com.godzillajim.betterprogramming.domain.repositories;

import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryById(Long categoryId);
    Optional<Category> findCategoryByName(String categoryName);
    List<Category> findByNameContainsAndArchived(String categoryName, Boolean archived);
    List<Category> findTop10ByOrderByCreatedDateDesc();
}
