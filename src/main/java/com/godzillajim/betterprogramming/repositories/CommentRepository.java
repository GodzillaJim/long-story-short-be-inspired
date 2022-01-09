package com.godzillajim.betterprogramming.repositories;

import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
