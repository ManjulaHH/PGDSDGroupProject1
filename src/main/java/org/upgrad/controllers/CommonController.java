package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.upgrad.common.Constants;
import org.upgrad.models.Category;
import org.upgrad.models.Question;
import org.upgrad.models.User;
import org.upgrad.repository.CategoryRepository;
import org.upgrad.repository.QuestionRepository;
import org.upgrad.services.CategoryService;
import org.upgrad.services.QuestionService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommonController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    QuestionService questionService;

    @GetMapping("/api/categories/all")
    public ResponseEntity getAllCategories(HttpSession session) {
      /*  User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }*/
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity(categories,HttpStatus.OK);
    }

    @GetMapping("/api/questions/all")
    public ResponseEntity getAllQuestions(HttpSession session){
       /* User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }*/
        List<Question> questions = questionService.getAllQuestions();
        return new ResponseEntity(questions,HttpStatus.OK);

    }


}
