package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.upgrad.models.Answer;
import org.upgrad.models.Like;
import org.upgrad.models.Notification;
import org.upgrad.models.User;
import org.upgrad.repository.AnswerRepository;
import org.upgrad.repository.LikesRepsository;
import org.upgrad.repository.NotificationRepository;
import org.upgrad.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl  implements LikeService {

    @Autowired
    LikesRepsository likesRepsository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;


    public LikeServiceImpl() {

    }

    @Override
    public void save(Like likeObj) {
       /* Answer answerObj = answerRepository.findByAnswerId(likeObj.getAnswer().getId());
        if(answerObj != null){
            likeObj.setAnswer(answerObj);
            User user  = userRepository.findUserByUserId(likeObj.getUser().getId());
            likeObj.setUser(user);
            likesRepsository.save(likeObj);
*/
        Optional userLogedIn = userRepository.findById(likeObj.getUser().getId());
        if (userLogedIn.isPresent()) {
            likeObj.setUser((User) userLogedIn.get());
        }
        Optional answer = answerRepository.findById(likeObj.getAnswer().getId());
        Answer answerObj = null;
        if (answer.isPresent()) {
             answerObj = (Answer) answer.get();
            likeObj.setAnswer(answerObj);

        }

        likesRepsository.save(likeObj);

        String content = "User with userId " + likeObj.getUser().getId() + " has liked your answer with answerId " + answerObj.getId();
        Notification notification = new Notification();
        notification.setMessage(content);
        notification.setRead(false);
        notification.setUser(answerObj.getUser());
        notificationRepository.save(notification);

    }



    @Override
    public Like getLikes(Integer userId, Integer answerId) {

        Like like= likesRepsository.getByUserAndAnswer(userId,answerId);
        return like;
    }

    @Override
    public User getUserId(Integer answerId, Integer userId) {
        Like like = likesRepsository.getByUserAndAnswer(userId,answerId);
        if(like == null){
            return null;
        }else{
            return like.getUser();
        }

    }

    @Override
    public Integer getCountByAnswer(Integer answerId) {
        return likesRepsository.getCountByAnswer(answerId);
    }

    @Override
    public void unLike(Integer answerId, Integer userId) {
        likesRepsository.deleteLikesByAnswerIdAndUserId(answerId,userId);


    }
}
