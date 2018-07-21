package org.upgrad.services;

import org.upgrad.models.Like;
import org.upgrad.models.User;

import java.util.List;

public interface LikeService {
    Like getLikes(Integer userId, Integer answerId);
    User getUserId(Integer answerId, Integer userId);
    Integer getCountByAnswer(Integer answerId);
    void save(Like likeObj);
    void unLike(Integer answerId, Integer userId);
}
