package com.godzillajim.betterprogramming.repositories;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleContainingOrContentContaining(String title, String content);
    Optional<Blog> findBlogById(Long blogId);
    Optional<Blog> findBlogByTitle(String title);
    List<Blog> findBlogByArchivedTrue();
    List<Blog> findBlogsByCategory(Category category);
    List<Blog> findTop5ByOrderByCreatedDateDesc();
    List<Blog> findTop5ByOrderByViewsDesc();
    List<Blog> findTop5ByTitleContainsOrContentContainsOrCategory(String query, String title, Category category);

}