package org.upgrad.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.common.Constants;
import org.upgrad.models.Category;
import org.upgrad.models.QuestionCategory;
import org.upgrad.models.Question;
import org.upgrad.models.User;
import org.upgrad.repository.CategoryRepository;
import org.upgrad.repository.QuestionCategoryRepository;
import org.upgrad.repository.QuestionRepository;
import org.upgrad.repository.UserRepository;
import org.upgrad.services.QuestionService;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class QuestionController {

    @Autowired
    private UserService userService;
    @Autowired
    QuestionService questionService;


    @PostMapping("/api/question")
    public ResponseEntity createQuestion(@RequestParam("categoryId") Set<Integer> categoryIds, @RequestParam("question") String question, HttpSession session) {
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }


        Question questionObj = new Question();
        questionObj.setUser(currUser);
        questionObj.setContent(question);
        Date date = new Date();
        questionObj.setDate(date);
        questionService.save(questionObj,categoryIds);



        return new ResponseEntity("Question added successfully.", HttpStatus.OK);


    }

    @GetMapping("/api/question/all/{categoryId}")
    public ResponseEntity getAllQuestionsByCategory(@PathVariable int categoryId, HttpSession session){
        User currUser = (User)session.getAttribute("currUser");

        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        List<Question> questions = questionService.getAllQuestionsByCategory(categoryId);

        return new ResponseEntity(questions, HttpStatus.OK);


    }
    @GetMapping("/api/question/all")
    public ResponseEntity getAllQuestionsByUser(HttpSession session){
        User currUser = (User)session.getAttribute("currUser");

        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        List<Question> questions = questionService.getAllQuestionsByUser(currUser.getId());
        return new ResponseEntity(questions, HttpStatus.OK);

    }

    @DeleteMapping("/api/question/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable int questionId,HttpSession session) {
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        boolean isAdmin = currUser.getRole().equalsIgnoreCase(Constants.ADMIN);

        Integer questionByUserId =  questionService.findUserIdfromQuestion(questionId);
        if(questionByUserId == -1){

            return new ResponseEntity("Please enter valid question id", HttpStatus.BAD_REQUEST);

        }
        if(isAdmin || (currUser.getId()==questionByUserId)){
            questionService.deleteQuestionById(questionId);
            return new ResponseEntity("Question with questionId "+ questionId + " deleted successfully.", HttpStatus.OK);

        }else{
            return new ResponseEntity(Constants.NO_RIGHTS+ " delete this question!", HttpStatus.UNAUTHORIZED);

        }
    }

}
