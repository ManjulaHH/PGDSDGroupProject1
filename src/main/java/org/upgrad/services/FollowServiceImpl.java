package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.upgrad.models.Category;
import org.upgrad.models.Follow;
import org.upgrad.models.User;
import org.upgrad.repository.CategoryRepository;
import org.upgrad.repository.FollowRepository;
import org.upgrad.repository.UserRepository;

@Service
public class FollowServiceImpl implements FollowService{
    @Autowired
    FollowRepository followRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;
    public FollowServiceImpl(){

    }

    @Override
    public Follow findByUserAndCategory(Integer categoryId, Integer userId) {
        Follow follow =followRepository.findbyUserIdAndCategoryId(userId,categoryId);
        return follow;
    }

    @Override
    public void save(Follow follow) {
        Category category = categoryRepository.findCategoryById(follow.getCategory().getId());
        User user = userRepository.findUserByUserId(follow.getUser().getId());
        follow.setUser(user);
        if(category != null){
            follow.setCategory(category);
            followRepository.save(follow);
        }
    }

    @Override
    public Integer findUserId(Integer categoryId, Integer userId) {
        Follow follow =followRepository.findbyUserIdAndCategoryId(userId,categoryId);
        if(follow == null){
            return -1;
        }else{
            return follow.getUser().getId();
        }
    }

    @Override
    public void unFollow(Integer categoryId, Integer userId) {
        followRepository.deleteByCategoryIdUserId(categoryId,userId);
    }
}
