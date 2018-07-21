package org.upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.common.Constants;
import org.upgrad.models.Category;
import org.upgrad.models.User;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/admin/category")
    public ResponseEntity categoriesCreation(@RequestParam("categoryTitle") String title, @RequestParam("description") String description,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        boolean isAdmin = currUser.getRole().equalsIgnoreCase(Constants.ADMIN);
        if(isAdmin){
            Category category = new Category();
            category.setTitle(title);
            category.setDescription(description);
            userService.saveCategory(category);
            return new ResponseEntity(title + " category added successfully.", HttpStatus.CREATED);


        }
        else{
            return new ResponseEntity(Constants.NO_RIGHTS+Constants.ADD_CATEGROY, HttpStatus.UNAUTHORIZED);

        }
    }

    @GetMapping("/api/admin/users/all")
    public ResponseEntity getAllUsers(HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        boolean isAdmin = currUser.getRole().equalsIgnoreCase(Constants.ADMIN);
        if(isAdmin){
            List<User> users = userService.getAllUsers();
            return new ResponseEntity(users, HttpStatus.OK);
        }else{
            return new ResponseEntity(Constants.NO_RIGHTS+" access all users!", HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/api/admin/user/{userId}")
    public ResponseEntity deleteUser(@PathVariable Integer userId,HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        boolean isAdmin = currUser.getRole().equalsIgnoreCase("admin");

        if(isAdmin){
            userService.deleteUserById(userId);
            return new ResponseEntity("User with userId "+ userId + " deleted successfully!", HttpStatus.OK);

        }else{
            return new ResponseEntity(Constants.NO_RIGHTS+" delete a user!", HttpStatus.UNAUTHORIZED);
        }
    }

}
