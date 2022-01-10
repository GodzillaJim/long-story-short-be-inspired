package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import com.godzillajim.betterprogramming.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    public List<Comment> getCommentsByBlog(Blog blog){
        return commentRepository.findCommentsByBlog(blog);
    }
    public Comment addNewComment(Comment comment){
      return commentRepository.save(comment);
    }
    public boolean deleteComment(Long commentId){
        Comment comment = getCommentDetails(commentId);
        if(comment != null){
            commentRepository.delete(comment);
            return true;
        }
        return false;
    }
    public Comment getCommentDetails(Long commentId){
        return commentRepository.getById(commentId);
    }
}
