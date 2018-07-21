package org.upgrad.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.upgrad.models.Category;
import org.upgrad.models.User;

import java.util.List;

public interface UserService {
    String findUserByUsername(String userName);
    String findUserByEmail(String email);
    String  findUserPassword(String userName);
    String  findUserRole(String userName);
    List<User> getAllUsers();
    Integer findUserId(String userName);
    User findByUserName(String userName);
    void save(User user);
    boolean isAdmin(String userName);

    void deleteUserById(Integer userId);
    public void saveCategory(Category category);

}
