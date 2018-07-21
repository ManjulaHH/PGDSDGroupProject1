package org.upgrad.controllers;

import com.sun.org.apache.xerces.internal.util.HTTPInputSource;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.common.Constants;
import org.upgrad.models.Answer;
import org.upgrad.models.Comment;
import org.upgrad.models.Notification;
import org.upgrad.models.User;
import org.upgrad.services.AnswerService;
import org.upgrad.services.CommentService;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/api/comment")
    public ResponseEntity giveComment(@RequestParam Integer answerId, @RequestParam String comment,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");

        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        Comment commentObj = new Comment();
        commentObj.setContent(comment);
        commentObj.setUser(currUser);
        Answer answerObj = new Answer();
        answerObj.setId(answerId);
        commentObj.setAnswer(answerObj);
        commentService.save(commentObj);
        return new ResponseEntity(" answerId "+  answerId + " commented successfully.",HttpStatus.OK);

    }




    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer commentId,  HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        Integer commentedByUserId = commentService.findUserIdfromComment(commentId);

        boolean isAdmin = currUser.getRole().equalsIgnoreCase(Constants.ADMIN);

        if(commentedByUserId == currUser.getId() || isAdmin){
            commentService.deleteComment(commentId);
            return new ResponseEntity(" Comment with commentId "+ commentId + " deleted successfully.",HttpStatus.OK);

        }else{
            return new ResponseEntity(Constants.NO_RIGHTS+" delete this comment!", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/api/comment/{commentId}")
    public ResponseEntity editComment(@PathVariable Integer commentId,@RequestParam String comment,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        Integer commentedByUserId = commentService.findUserIdfromComment(commentId);

        boolean isAdmin = currUser.getRole().equalsIgnoreCase(Constants.ADMIN);

        if(commentedByUserId == currUser.getId() || isAdmin){
            commentService.editComment(commentId,comment);
            return new ResponseEntity(" Comment with commentId "+ commentId +" edited successfully.",HttpStatus.OK);

        }else{
            return new ResponseEntity(Constants.NO_RIGHTS+" edit this comment.", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/api/comment/all/{answerId}")
    public ResponseEntity getAllCommentsByAnswer(@PathVariable Integer answerId,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        List<Comment> comments = commentService.getAllComments(answerId);

        if(comments.size() == 0){
            return new ResponseEntity("No comments provided ", HttpStatus.OK);
        }
        return new ResponseEntity(comments,HttpStatus.OK);
    }


}
