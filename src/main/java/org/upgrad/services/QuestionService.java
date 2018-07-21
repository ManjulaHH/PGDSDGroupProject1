package org.upgrad.services;

import org.upgrad.models.Question;

import java.util.List;
import java.util.Set;

public interface QuestionService {
    List<Question> getAllQuestions();
    List<Question> getAllQuestionsByUser(Integer userId);
    List<Question>  getAllQuestionsByCategory(Integer categoryId);
    Integer findUserIdfromQuestion(Integer questionId);
    void save(Question question,Set<Integer> categoryIds);
    void deleteQuestionById(Integer questionID);
    Question findByQuestionId(Integer id);
}
