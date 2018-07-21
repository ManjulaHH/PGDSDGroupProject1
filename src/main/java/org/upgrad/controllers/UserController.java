package org.upgrad.controllers;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upgrad.common.Constants;
import org.upgrad.common.Util;
//import org.upgrad.models.Notification;
import org.upgrad.models.Notification;
import org.upgrad.models.User;
import org.upgrad.models.UserProfile;
import org.upgrad.repository.UserProfileRepository;
import org.upgrad.repository.UserRepository;
import org.upgrad.services.NotificationService;
import org.upgrad.services.UserProfileService;
import org.upgrad.services.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
public class UserController {


    @Autowired
    UserService userService;
    @Autowired
    UserProfileService userProfileService;

    @Autowired
     NotificationService notificationService;

    @PostMapping("/api/user/signup")

    public ResponseEntity signup(@RequestParam("userName") String uname, @RequestParam("password") String password, @RequestParam("firstName") String firstName, @RequestParam(required = false,name ="lastName") String lastName, @RequestParam("email") String email, @RequestParam("country") String country, @RequestParam(required = false,name ="aboutMe") String aboutMe, @RequestParam("dob") String dob, @RequestParam(required = false,name ="contactNumber") String phoneNum) {


        if(uname == null || uname.isEmpty()){
            return new ResponseEntity("userName is not provided", HttpStatus.BAD_REQUEST);
        }
        else{
            String username =  userService.findUserByUsername(uname);
            if(username != null && username.equalsIgnoreCase(uname)){
                return new ResponseEntity(uname + "Try any other Username, this Username has already been taken.", HttpStatus.BAD_REQUEST);
            }
        }

        String emailExist =  userService.findUserByEmail(email);
        if(emailExist != null && emailExist.equalsIgnoreCase(email)){
            return new ResponseEntity(uname + "This user has already been registered, try with any other emailId.", HttpStatus.BAD_REQUEST);
        }

        String hashedPassword = Util.hashPassword(password);
        User user = new User(uname, email, "user");
        user.setPassword(hashedPassword);


        UserProfile userProfile = new UserProfile();
        userProfile.setFirstName(firstName);
        userProfile.setLastName(lastName);
        userProfile.setAboutMe(aboutMe);
        userProfile.setContactNumber(phoneNum);
        userProfile.setCountry(country);
        userProfile.setUser(user);
        try {
            userProfile.setDob(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userProfileService.save(userProfile);

        return new ResponseEntity(uname + " successfully registered", HttpStatus.OK);
    }




    @PostMapping("/api/user/login")
    public ResponseEntity login(@RequestParam("userName") String uname, @RequestParam("password") String password,HttpSession session) {
        if(uname == null || uname.isEmpty() || password == null || password.isEmpty()){
            return new ResponseEntity("Invalid Credentials", HttpStatus.BAD_REQUEST);
        }
        String storedPassword = userService.findUserPassword(uname);
        if(storedPassword == null ){
            return new ResponseEntity("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }
        String hashOfUserEnteredPassword = Util.hashPassword(password) ;

        boolean success = hashOfUserEnteredPassword.equals(storedPassword);
        if(success){
            User userInfo = userService.findByUserName(uname);
            String storedRole = userService.findUserRole(uname);
            session.setAttribute("currUser",userInfo);
            if(userInfo.getRole().equalsIgnoreCase(Constants.ADMIN)){

                return new ResponseEntity("You have logged in as admin!", HttpStatus.OK);
            }
            return new ResponseEntity("You have logged in successfully!", HttpStatus.OK);
        }
        else{
            return new ResponseEntity("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }


    }


    @GetMapping("/api/user/userprofile/{userId}")
    public ResponseEntity getUserProfile(@PathVariable int userId,HttpSession session) {
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity("Please Login first to access this endpoint", HttpStatus.UNAUTHORIZED);

        }
        UserProfile userProfile = userProfileService.findUserProfileByUserId(userId);
        if(userProfile == null){
            return new ResponseEntity("User Profile not found!", HttpStatus.OK);
        }
        else{
            return new ResponseEntity(userProfile, HttpStatus.OK);

        }
    }

    @PostMapping("/api/user/logout")
    public ResponseEntity signout(HttpSession session){
        User currUser = (User)session.getAttribute("currUser");

        if(currUser == null){
            return new ResponseEntity("You are currently not logged in", HttpStatus.UNAUTHORIZED);
        }
        else{
            session.removeAttribute("currUser");
            return new ResponseEntity("You have logged out successfully", HttpStatus.OK);
        }
    }

    @GetMapping("/api/user/notification/all")
    public ResponseEntity getAllNotifications(HttpSession session) {
        User currUser = (User) session.getAttribute("currUser");

        if (currUser == null) {
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        List<Notification> notificationList = notificationService.getAllNotifications(currUser.getId());
        if (notificationList.size() == 0) {
            return new ResponseEntity("No  notifications", HttpStatus.OK);

        }

        List<Notification> notificationListToUpdate = new ArrayList<Notification>();
        for (Notification notification : notificationList) {
            if (!notification.isRead()) {
                notificationListToUpdate.add(notification);
            }
        }
        notificationService.markReadNewNotifications(notificationListToUpdate);
        return new ResponseEntity(notificationList, HttpStatus.OK);
    }

    @GetMapping("/api/user/notification/new")
    public ResponseEntity getNewNotifications(HttpSession session){
        User currUser = (User)session.getAttribute("currUser");
        if(currUser == null){
            return new ResponseEntity(Constants.LOGIN_MSG, HttpStatus.UNAUTHORIZED);

        }
        List<Notification> notificationList = notificationService.getNewNotifications(currUser.getId());

        if (notificationList.size() == 0) {
            return new ResponseEntity("No new notifications", HttpStatus.OK);

        }
        List<Notification> notificationListToShow=new ArrayList<Notification>();


        for(Notification notification: notificationList){
            notificationListToShow.add(notification);
        }

        notificationService.markReadNewNotifications(notificationList);
        return new ResponseEntity(notificationListToShow,HttpStatus.OK);
    }


}
