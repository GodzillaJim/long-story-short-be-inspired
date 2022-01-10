package com.godzillajim.betterprogramming.repositories;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findTagsByTagName(String tagName);
    List<Tag> findTagByBlog(Blog blog);
}
