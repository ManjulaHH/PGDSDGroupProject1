package org.upgrad.services;

import io.swagger.models.auth.In;
import org.upgrad.models.Answer;

import java.util.List;
import java.util.Map;

public interface AnswerService {
    Integer findUserIdfromAnswer(int answerId);
    List<Answer> getAllAnswersToQuestion(int questionId);
    List<Answer> getAllAnswersByUser(Integer userId);
    void save(Answer answer,Integer questionId);
    void editAnswer(String answer,Integer answerId);
    void deleteAnswer(Integer answerId);
    Map<String,Integer>  getAllAnswerLikesCountForQuestion(Integer questionId);

}