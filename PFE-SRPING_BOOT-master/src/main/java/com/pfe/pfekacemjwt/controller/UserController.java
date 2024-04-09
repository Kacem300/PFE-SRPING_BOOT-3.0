package com.pfe.pfekacemjwt.controller;

import com.pfe.pfekacemjwt.entitiy.User;
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
import java.util.Set;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initRolesAndUsers() {
        userService.initRoleAndUser();
    }

    @PutMapping(value = "/updateCurrentUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User updateCurrentUser(@RequestPart("user") User updatedUser,
                                  @RequestPart("imageFile") MultipartFile[] file) {
        try {
            Set<imageModel> userImages = uploadImage(file);
            updatedUser.setUserImages(userImages);
            return userService.updateCurrentUser(updatedUser);
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Set<imageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<imageModel> imageModels = new HashSet<>();
        for (MultipartFile file : multipartFiles) {
            imageModel userImage = new imageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(userImage);
        }
        return imageModels;
    }



    //    @PutMapping("/updateCurrentUser")
//    public User updateCurrentUser(@RequestBody User updatedUser) {
//        return userService.updateCurrentUser(updatedUser);
//    }
    @GetMapping({"/getCurrentUser"})
    public User getCurrentUser(){
        return userService.getCurrentUser();
    }

    @GetMapping({"/"})
    public String home(){
        return "Welcome to the home page";
    }

    @PostMapping({"/registerNewUser"})
    public User registerNewUser(@RequestBody User user){
        return   userService.registerNewUser(user);

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
}
