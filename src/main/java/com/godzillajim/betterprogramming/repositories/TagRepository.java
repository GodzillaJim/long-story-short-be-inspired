package com.godzillajim.betterprogramming.repositories;

import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagByTagName(String tagName);
    List<Tag> findTagsByTagNameContains(String query);
    Optional<Tag> findTagById(Long id);
    List<Tag> findTop20ByOrderByCreatedDateDesc();
}

