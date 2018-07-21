package org.upgrad.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.upgrad.models.Comment;

import java.util.List;


public interface CommentService {
    Integer findUserIdfromComment(Integer commentId);
    List<Comment> getAllComments(Integer answerId);
    void save(Comment commentObj);
    void editComment(Integer commentId,String comment);
    void deleteComment(Integer commentId);
}