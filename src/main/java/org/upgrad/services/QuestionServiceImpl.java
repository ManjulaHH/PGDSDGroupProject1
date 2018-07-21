package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.upgrad.models.Category;
import org.upgrad.models.Question;
import org.upgrad.models.QuestionCategory;
import org.upgrad.models.User;
import org.upgrad.repository.CategoryRepository;
import org.upgrad.repository.QuestionCategoryRepository;
import org.upgrad.repository.QuestionRepository;
import org.upgrad.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    QuestionCategoryRepository questionCategoryRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;
    public QuestionServiceImpl(){

    }


    public String createQuestion(Set<Integer> categoryIds, String question,User loggedInUser) {

        Question questionObj = new Question();
        questionObj.setUser(loggedInUser);
        questionObj.setContent(question);
        questionRepository.save(questionObj);

        for(Integer categoryId : categoryIds){

        }


        return "Question added successfully.";
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.getAllQuestions();
    }

    @Override
    public List<Question> getAllQuestionsByUser(Integer userId) {
        List<Question> questions = questionRepository.findByUserId(userId);
        return  questions;
    }

    @Override
    public List<Question> getAllQuestionsByCategory(Integer categoryId) {
        List<Integer> question_ids = questionCategoryRepository.findByCategoryId(categoryId);

        if(question_ids.size() == 0){
            return null;
        }
        List<Question> listOfQuestions = new ArrayList<Question>();

        for(Integer questionId : question_ids){
            Question questionObj = questionRepository.findByQuestionId(questionId);
            if(questionObj != null){
                listOfQuestions.add(questionObj);
            }
        }
        return listOfQuestions;
    }

    @Override
    public Integer findUserIdfromQuestion(Integer questionId) {
        Question question = findByQuestionId(questionId);
        if(question != null){
            return question.getUser().getId();
        }
        return -1;
    }

    @Override
    public void save(Question question,Set<Integer> categoryIds) {
        Set<Category> categoryObjs = new HashSet<Category>();
        for(int categoryId: categoryIds){
            Category category = categoryRepository.findCategoryById(categoryId);
            if(category!=null){
                categoryObjs.add(category);
            }
        }
        List<User> users = userRepository.findUserByUserName(question.getUser().getUserName());
        User userObj = users.get(0);
        question.setUser(userObj);
        questionRepository.save(question);

        for(Category category : categoryObjs){
            QuestionCategory questCategory = new QuestionCategory();
            questCategory.setCategory(category);
            questCategory.setQuestion(question);
            questionCategoryRepository.save(questCategory);
        }
    }

    @Override
    public void deleteQuestionById(Integer questionID) {
        questionRepository.deletByQuestionId(questionID);
    }

    @Override
    public Question findByQuestionId(Integer id) {
        return questionRepository.findByQuestionId(id);
    }
}