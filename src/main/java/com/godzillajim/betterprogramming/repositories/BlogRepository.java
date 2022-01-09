package com.godzillajim.betterprogramming.repositories;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findByTitleContainingOrContentContaining(String title, String content);
}
