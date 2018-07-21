package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.common.Constants;
import org.upgrad.models.Answer;
import org.upgrad.models.Question;
import org.upgrad.models.User;
import org.upgrad.repository.*;
import org.upgrad.services.AnswerService;
import org.upgrad.services.LikeService;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class AnswerController {
    @Autowired
    AnswerService answerService;
    @Autowired
    UserService userService;



    @PostMapping("/api/answer")
    public ResponseEntity createAnswer(@RequestParam Integer questionId, @RequestParam String answer,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        Answer answerObj  = new Answer();
        answerObj.setAns(answer);
        answerObj.setUser(currUser);
        answerObj.setAns(answer);
        answerService.save(answerObj, questionId );

        return new ResponseEntity( "Answer to questionId " + questionId + " added successfully",HttpStatus.OK);

    }
    @PutMapping("/api/answer/{answerId}")
    public ResponseEntity  editAnswer(@PathVariable int answerId,@RequestParam String answer,HttpSession session) {
        User currUser = (User) session.getAttribute("currUser");
        if (currUser == null) {
            return new ResponseEntity("Please Login first to access this endpoint", HttpStatus.UNAUTHORIZED);

        }
        boolean isAdmin = currUser.getRole().equalsIgnoreCase(Constants.ADMIN);

        Integer answeredUserId = answerService.findUserIdfromAnswer(answerId);
        if (answeredUserId < 0) {
            return new ResponseEntity("Please enter valid answer id", HttpStatus.BAD_REQUEST);

        }
        if (isAdmin || (currUser.getId() == answeredUserId)) {
            answerService.editAnswer(answer,answerId);
            return new ResponseEntity(" Answer with answerId " + answerId +" edited successfully.", HttpStatus.OK);

        } else {
            return new ResponseEntity(Constants.NO_RIGHTS+" edit this answer.", HttpStatus.UNAUTHORIZED);


        }
    }

    @GetMapping("/api/answer/all/{questionId}")
    public ResponseEntity getAllAnswersToQuestion(@PathVariable int questionId,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }

        List<Answer> listOfAnswer = answerService.getAllAnswersToQuestion(questionId);
        if(listOfAnswer.size() == 0){
            return new ResponseEntity("Please enter valid question id", HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity(listOfAnswer, HttpStatus.OK);
    }

    @GetMapping("/api/answer/all")
    public ResponseEntity getAllAnswersByUser(HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        Integer currUserId = userService.findUserId(currUser.getUserName());
        List<Answer> listOfAnswer = answerService.getAllAnswersByUser(currUserId);
        if(listOfAnswer == null || listOfAnswer.size() == 0){
            return new ResponseEntity("No answers provided by you", HttpStatus.BAD_REQUEST);

        }

        return new ResponseEntity(listOfAnswer, HttpStatus.OK);

    }

    @DeleteMapping("/api/answer/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable int answerId,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        boolean isAdmin = currUser.getRole().equalsIgnoreCase("admin");

        Integer answeredUserId = answerService.findUserIdfromAnswer(answerId);
        if(answeredUserId < 0 ){
            return new ResponseEntity("Please enter valid answer id", HttpStatus.BAD_REQUEST);

        }
        if(isAdmin || (currUser.getId()== answeredUserId)){
            answerService.deleteAnswer(answerId);
            return new ResponseEntity(" Answer with answerId " + answerId + " deleted successfully.", HttpStatus.OK);

        }else{
            return new ResponseEntity(Constants.NO_RIGHTS+ " delete this answer.", HttpStatus.UNAUTHORIZED);

        }
    }

    @GetMapping("/api/answer/likes/{questionId}")
    public ResponseEntity getAllAnswersByLikes(@PathVariable int questionId,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }

        Map<String,Integer> answerWithLikes = answerService.getAllAnswerLikesCountForQuestion(questionId);


        LinkedHashMap<String,Integer> reverseSortedMap = new LinkedHashMap<>();

        answerWithLikes.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return new ResponseEntity(reverseSortedMap, HttpStatus.OK);
    }


}

