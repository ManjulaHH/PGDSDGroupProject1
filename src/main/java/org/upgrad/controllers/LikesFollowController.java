package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.common.Constants;
import org.upgrad.models.*;
import org.upgrad.services.AnswerService;
import org.upgrad.services.FollowService;
import org.upgrad.services.LikeService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class LikesFollowController {
    @Autowired
    LikeService likeService;


    @Autowired
    FollowService followService;

    @Autowired
    AnswerService answerService;
    @PostMapping("/api/like/{answerId}")
    public ResponseEntity giveLikes(@PathVariable int answerId, HttpSession session)
    {
        User currUser = (User)session.getAttribute("currUser");

        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        Like like = likeService.getLikes(currUser.getId(),answerId);

        if(like != null){
            return new ResponseEntity("You have already liked this answer!", HttpStatus.BAD_REQUEST);
        }
        Answer answerObj = new Answer();
        answerObj.setId(answerId);
        Like likes = new Like();
        likes.setUser(currUser);
        likes.setAnswer(answerObj);

        likeService.save(likes);


        return new ResponseEntity(" answerId "+ answerId +" liked successfully.", HttpStatus.OK);


    }

    @PostMapping("/api/follow/{categoryId}")
    public ResponseEntity addFollowCategory(@PathVariable int categoryId,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }

        Follow follow = followService.findByUserAndCategory(categoryId,currUser.getId());
        if(follow != null){
            return new ResponseEntity("You have already followed this category!", HttpStatus.BAD_REQUEST);
        }


        Category categoryObj = new Category();
        categoryObj.setId(categoryId);
        Follow followObj = new Follow();
        followObj.setUser(currUser);
        followObj.setCategory(categoryObj);
        followService.save(followObj);

        return new ResponseEntity(" categoryId " + categoryId + " followed successfully.", HttpStatus.OK);



    }
    @DeleteMapping("/api/unlike/{answerId}")
    public ResponseEntity unlike(@PathVariable int answerId,HttpSession session ){
        User currUser = (User)session.getAttribute("currUser");

        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }


        User likedUser = likeService.getUserId(answerId,currUser.getId());

        if(likedUser == null || likedUser.getId() != currUser.getId()){
            return new ResponseEntity("You have not liked this answer", HttpStatus.BAD_REQUEST);
        }
        likeService.unLike(answerId,currUser.getId());

        return new ResponseEntity(" You have unliked answer with answerId " + answerId + " successfully.",HttpStatus.OK);


    }

    @DeleteMapping("/api/unfollow/{categoryId}")
    public ResponseEntity unFollow(@PathVariable int categoryId,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");

        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        Integer followedUserId = followService.findUserId(1,currUser.getId());
        if(followedUserId == null || followedUserId <0){
            return new ResponseEntity("You are currently not following this category", HttpStatus.BAD_REQUEST);
        }

        followService.unFollow(categoryId,currUser.getId());
        return new ResponseEntity(" You have unfollowed the category with categoryId "+ categoryId + " successfully.",HttpStatus.OK);

    }



}
