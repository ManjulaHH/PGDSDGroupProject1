package org.upgrad.services;

import org.upgrad.models.Follow;
import org.upgrad.models.User;

public interface FollowService {
    Integer findUserId(Integer categoryId, Integer userId);
    Follow findByUserAndCategory(Integer categoryId, Integer userId);
    void save(Follow follow);
    void unFollow(Integer categoryId, Integer userId);
}
