package com.godzillajim.betterprogramming.repositories;

import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagsByTagName(String tagName);
}
