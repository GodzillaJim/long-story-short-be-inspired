package com.godzillajim.betterprogramming.services;

import com.godzillajim.betterprogramming.domain.entities.blog.Comment;
import com.godzillajim.betterprogramming.domain.mappers.CommentBody;
import com.godzillajim.betterprogramming.repositories.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    public Comment addNewComment(CommentBody commentBody){
        Comment comment = new Comment();
        comment.setLastName(commentBody.getLastName());
        comment.setContent(commentBody.getContent());
        comment.setFirstName(commentBody.getFirstName());
      return  commentRepository.save(comment);
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
