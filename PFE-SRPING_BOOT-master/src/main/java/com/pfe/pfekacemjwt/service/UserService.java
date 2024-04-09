package com.pfe.pfekacemjwt.service;

import com.pfe.pfekacemjwt.dao.RoleDao;
import com.pfe.pfekacemjwt.dao.UserDao;
import com.pfe.pfekacemjwt.entitiy.Role;
import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder ;


    public User registerNewUser(User user){
        Role role = roleDao.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        user.setUserImages(null);
        user.setUserEmail(null);
        return userDao.save(user);
    }


    public void initRoleAndUser()  {
        Role adminRole = new Role();
        adminRole.setRolename("Admin");
        adminRole.setRoledescription("Admin Role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRolename("User");
        userRole.setRoledescription("Default role ");
        roleDao.save(userRole);


        User adminUser = new User();
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastname("admin");
        adminUser.setUserName("admin123");
        adminUser.setUserEmail("gmailtest");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

        User user = new User();
        user.setUserFirstName("kacem");
        user.setUserLastname("benbrahim");
        user.setUserName("kacem300");
        user.setUserEmail("gmailtest");
        user.setUserPassword(getEncodedPassword("kacem@pass"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        user.setUserImages(null);
//        Path path = Paths.get("path/to/your/image.jpg");
//        byte[] imageBytes = Files.readAllBytes(path);
//        user.setUserImage(imageBytes);

        userDao.save(user);


    }
    public String getEncodedPassword(String password){
        return  passwordEncoder.encode(password);
    }


    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        return userDao.findById(currentUser ).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User updateCurrentUser(User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();

        User user = userDao.findById(currentUser).orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserName(updatedUser.getUserName());
        user.setUserFirstName(updatedUser.getUserFirstName());
        user.setUserLastname(updatedUser.getUserLastname());
        user.setUserEmail(updatedUser.getUserEmail());
        user.setUserPassword(updatedUser.getUserPassword());
        user.setUserImages(updatedUser.getUserImages());
        user.setRole(updatedUser.getRole());

        return userDao.save(user);
    }

}

