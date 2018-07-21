package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.upgrad.models.Answer;
import org.upgrad.models.Comment;
import org.upgrad.models.Notification;
import org.upgrad.models.User;
import org.upgrad.repository.AnswerRepository;
import org.upgrad.repository.CommentRepository;
import org.upgrad.repository.NotificationRepository;
import org.upgrad.repository.UserRepository;

import java.util.List;
import java.util.Optional;
@Service

public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    NotificationRepository notificationRepo;

    @Autowired
    AnswerRepository answerRepo;

    public CommentServiceImpl(){

    }


    public void editComment(Integer commentId,String commentStr){
        commentRepo.editComment(commentId,commentStr);
    }



    private boolean isAdminUser(User loggedInUserObj){
        return (loggedInUserObj.getRole().equalsIgnoreCase("admin"));
    }
    public void deleteComment(Integer commentId) {
        commentRepo.deletesById(commentId);

    }

    public List<Comment> getAllCommentsByAnswer(Integer answerId) {
        List<Comment> comments = commentRepo.getAllCommentsByAnswerId(answerId);
        return comments;

    }


    @Override
    public Integer findUserIdfromComment(Integer commentId) {
        Comment commentObj = commentRepo.getCommentById(commentId);
        if(commentObj == null){
            return -1;
        }else{
            return  commentObj.getUser().getId();
        }
    }

    @Override
    public List<Comment> getAllComments(Integer answerId) {

        List<Comment> comments = commentRepo.getAllCommentsByAnswerId(answerId);
        return comments;
    }

    @Override
    public void save(Comment commentObj) {

        Answer answerObj = answerRepo.findByAnswerId(commentObj.getAnswer().getId());
        if(answerObj != null){
            commentObj.setAnswer(answerObj);
            User user  = userRepo.findUserByUserId(commentObj.getUser().getId());
            commentObj.setUser(user);
            commentRepo.save(commentObj);
            User userAnsweredObj = answerObj.getUser();
            String content = "User with userId "+ commentObj.getUser().getId() + " has commented on your answer with answerId " + answerObj.getId();
            Notification notification = new Notification();
            notification.setMessage(content);
            notification.setRead(false);
            notification.setUser(userAnsweredObj);
            notificationRepo.save(notification);
        }


    }


}
