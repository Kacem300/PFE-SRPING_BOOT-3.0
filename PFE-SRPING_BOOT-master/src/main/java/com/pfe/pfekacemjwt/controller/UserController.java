package com.pfe.pfekacemjwt.controller;

import com.pfe.pfekacemjwt.dao.UserDao;
import com.pfe.pfekacemjwt.dao.VerifyDao;
import com.pfe.pfekacemjwt.entitiy.User;
import com.pfe.pfekacemjwt.entitiy.UserCount;
import com.pfe.pfekacemjwt.entitiy.VerificationToken;
import com.pfe.pfekacemjwt.entitiy.imageModel;
import com.pfe.pfekacemjwt.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @Autowired
    private VerifyDao tokenRepository;
    @Autowired
    private UserDao userDao;


    @RequestMapping(value="/registrationConfirm", method = RequestMethod.GET)
    public void confirmRegistration(@RequestParam("token") String token) {
        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            System.out.println("Invalid token verification null");
        } else {
            User user = verificationToken.getUser();
            user.setEnabled(true); // Assuming you have a field 'enabled' in User class to mark the account as verified
            userDao.save(user); // Assuming userRepository is your User repository
            System.out.println("User verified successfully");
        }
    }


    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user){
        return   userService.registerNewUser(user);

    }
    @PostConstruct
    public void initRolesAndUsers() {
        userService.initRoleAndUser();
    }

    @PutMapping(value = "/updateCurrentUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User updateCurrentUser(@RequestPart("user") User updatedUser,
                                  @RequestPart("imageFile") MultipartFile file) {
        try {
            imageModel userImage = uploadImage(file);
            updatedUser.setUserImage(userImage);
            return userService.updateCurrentUser(updatedUser);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public imageModel uploadImage(MultipartFile multipartFile) throws IOException {
        imageModel userImage = new imageModel(
                multipartFile.getOriginalFilename(),
                multipartFile.getContentType(),
                multipartFile.getBytes()
        );
        return userImage;
    }




    @GetMapping({"/getCurrentUser"})
    public User getCurrentUser(){
        return userService.getCurrentUser();
    }

    @GetMapping({"/"})
    public String home(){
        return "Welcome to the home page";
    }


    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){

        return "this URL is only accessible to admin";

    }
    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "this URL is only accessible to the user";
    }
    @GetMapping("/getNewUserCount")
    public Long getNewUserCount() {
        return userService.getNewUserCount();
    }

    @GetMapping("/getTotalUserCount")
    public Long getTotalUserCount() {
        return userService.getTotalUserCount();
    }

    @GetMapping("/getUserCountsPerMonth")
    public List<UserCount> getUserCountsPerMonth() {
        return userService.getUserCountsPerMonth();
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
