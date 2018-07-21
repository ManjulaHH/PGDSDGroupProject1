package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.upgrad.models.UserProfile;
import org.upgrad.repository.UserProfileRepository;

import java.util.List;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public void save(UserProfile profle) {
        userProfileRepository.save(profle);
    }

    @Override
    public UserProfile findUserProfileByUserId(Integer userId) {
        List<UserProfile> profiles = userProfileRepository.findUserProfileByUserId(userId);
        if(profiles == null || profiles.isEmpty() ){
            return null;
        }else{
            return (UserProfile)profiles.get(0);
        }

    }
}
