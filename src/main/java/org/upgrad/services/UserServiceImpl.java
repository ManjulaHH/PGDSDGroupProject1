package org.upgrad.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.upgrad.common.Constants;
import org.upgrad.models.Category;
import org.upgrad.models.User;
import org.upgrad.repository.CategoryRepository;
import org.upgrad.repository.UserRepository;

import java.util.List;
@Service

public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepo;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public String findUserByUsername(String userName) {
        List<User> users = userRepo.findUserByUserName(userName);
        if(users.size() == 0) {
            return null;
        }else{
            return users.get(0).getUserName();
        }
    }



    @Override
    public String findUserByEmail(String email) {
        User user = userRepo.findUserByEmail(email);
        if(user == null){
            return null;
        }
        return user.getEmail();
    }

    @Override
    public String findUserPassword(String userName) {
        List<User> users = userRepo.findUserByUserName(userName);
        if(users.size() == 0) {
            return null;
        }else{
            return users.get(0).getPassword();
        }
    }

    @Override
    public String findUserRole(String userName) {
        List<User> users = userRepo.findUserByUserName(userName);
        if(users.size() == 0) {
            return null;
        }else{
            return users.get(0).getRole();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAllUsers();
    }

    @Override
    public Integer findUserId(String userName)
    {

        List<User> users = userRepo.findUserByUserName(userName);
        if(users == null || users.size() == 0) {
            return null;
        }else{
            return users.get(0).getId();
        }    }

    @Override
    public User findByUserName(String userName) {
        List<User> users = userRepo.findUserByUserName(userName);
        if(users == null || users.size() == 0) {
            return null;
        }else{
            return users.get(0);
        }
    }

    @Override
    public void save(User user) {
        userRepo.save(user);
    }

    @Override
    public boolean isAdmin(String userName) {
        String storedRole = findUserRole(userName);
        boolean isAdmin = storedRole.equalsIgnoreCase(Constants.ADMIN);
        return false;
    }

    @Override
    public void deleteUserById(Integer userId) {
        userRepo.deleteUserById(userId);
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}
