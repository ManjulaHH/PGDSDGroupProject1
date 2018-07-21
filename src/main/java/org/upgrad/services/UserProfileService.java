package org.upgrad.services;

import org.springframework.stereotype.Service;
import org.upgrad.models.UserProfile;

import java.util.List;


public interface UserProfileService {
    void save(UserProfile profle);
    UserProfile findUserProfileByUserId(Integer userId);
}
