package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Blog;
import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import com.godzillajim.betterprogramming.errors.ResourceNotFoundException;
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
    public void addNewComment(Comment comment){
         commentRepository.save(comment);
    }
    public boolean deleteComment(Long commentId){
        Comment comment = getCommentDetails(commentId);
        commentRepository.delete(comment);
        return true;
    }
    public Comment getCommentDetails(Long commentId){
        return commentRepository
                .findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException(String.format("Comment with this id: %s does not exist", commentId)));
    }
}
