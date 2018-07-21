package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.upgrad.models.Answer;
import org.upgrad.models.Notification;
import org.upgrad.models.Question;
import org.upgrad.models.User;
import org.upgrad.repository.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    QuestionRepository questionRepo;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    LikesRepsository likesRepsository;

    public AnswerServiceImpl(){

    }

    @Override
    public Integer findUserIdfromAnswer(int answerId) {
        Answer answer =  answerRepository.findByAnswerId(answerId);
        if(answer == null){
            return -1;
        }else{
            return answer.getUser().getId();
        }
    }

    @Override
    public List<Answer> getAllAnswersToQuestion(int questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @Override
    public List<Answer> getAllAnswersByUser(Integer userId) {
        return answerRepository.findByUserId(userId);
    }

    @Override
    public void save(Answer answerObj, Integer questionId) {
        Question question = questionRepo.findByQuestionId(questionId);
        if(questionRepo != null){
            answerObj.setQuestion(question);
            User user  = userRepo.findUserByUserId(answerObj.getUser().getId());
            answerObj.setUser(user);
            answerRepository.save(answerObj);

            String content = "User with userId " + answerObj.getUser().getId() + " has answered your question with questionId " + questionId;
            User userAskedQuestionObj = question.getUser();
            Notification notification = new Notification();
            notification.setMessage(content);
            notification.setRead(false);
            notification.setUser(userAskedQuestionObj);
            notificationRepository.save(notification);

        }

    }

    public void editAnswer(String answer,Integer answerId){
        answerRepository.updateById(answer,answerId);
    }

    @Override
    public void deleteAnswer(Integer answerId) {
        answerRepository.deleteAnswerById(answerId);
    }

    @Override
    public Map<String, Integer> getAllAnswerLikesCountForQuestion(Integer questionId) {
        Map<String,Integer> answerWithLikes = new HashMap<String,Integer>() ;

        List<Answer> listOfAnswer = getAllAnswersToQuestion(questionId);
        if(listOfAnswer == null || listOfAnswer.size() == 0){
            return answerWithLikes;
        }
        for(Answer answerObj : listOfAnswer){
            int answerId = answerObj.getId();
            Integer countOfLikes = likesRepsository.getCountByAnswer(answerId);
            answerWithLikes.put(answerObj.getAns(),countOfLikes);
        }
        return answerWithLikes;
    }
}
