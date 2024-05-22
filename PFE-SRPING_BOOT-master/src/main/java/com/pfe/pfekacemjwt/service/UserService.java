package com.pfe.pfekacemjwt.service;

import com.pfe.pfekacemjwt.dao.RoleDao;
import com.pfe.pfekacemjwt.dao.UserDao;
import com.pfe.pfekacemjwt.dao.VerifyDao;
import com.pfe.pfekacemjwt.entitiy.Role;
import com.pfe.pfekacemjwt.entitiy.User;
import com.pfe.pfekacemjwt.entitiy.UserCount;
import com.pfe.pfekacemjwt.entitiy.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PasswordEncoder passwordEncoder ;
    @Autowired
    private VerifyDao verifyDao;
    @Autowired
    private EmailSenderService emailSenderService;



    public void generateVerificationTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verifyDao.save(verificationToken);

        String message = "Your password reset token is: " + token;
        emailSenderService.sendEmail(user.getUserEmail(), "Password Reset Token", message);
    }

    public User findUserByEmail(String email) {
        return userDao.findByUserEmail(email);
    }

    public boolean validateVerificationToken(String token) {
        VerificationToken verificationToken = verifyDao.findByToken(token);
        return verificationToken != null;
    }

    public void changeUserPassword(User user, String newPassword) {
        user.setUserPassword(passwordEncoder.encode(newPassword));
        userDao.save(user);
    }


    public User registerNewUser(User user){
        Role role = roleDao.findById("User").get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        user.setUserImage(null);
        user.setEnabled(false);
        user.setRegistrationDate(new Date());
        User savedUser = userDao.save(user);

    // Generate a verification token
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken(token, savedUser);
        verifyDao.save(verificationToken);

    // Send an email to the user with the verification token
        String confirmationUrl = "http://localhost:4200/emailVerification?token=" + token;
    String message = "Please click the following link to verify your email: " + confirmationUrl;
    emailSenderService.sendEmail(savedUser.getUserEmail(), "Registration Confirmation", message);
        user.setRegistrationDate(new Date());

        return savedUser;}

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
        adminUser.setEnabled(true);
        adminUser.setRegistrationDate(new Date());
        userDao.save(adminUser);

        User user = new User();
        user.setUserFirstName("kacem");
        user.setUserLastname("benbrahim");
        user.setUserName("kacem300");
        user.setUserEmail("kacem.benbrahim@gmail.com");
        user.setUserPassword(getEncodedPassword("kacem@pass"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        user.setUserImage(null);
        user.setEnabled(true);
        user.setRegistrationDate(new Date());
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
        user.setUserImage(updatedUser.getUserImage());
        user.setRole(updatedUser.getRole());

        return userDao.save(user);
    }


    public List<UserCount> getUserCountsPerMonth() {
        // Get all users
        List<User> users = userDao.findAll();

        // Create a map to store the count of users per month
        Map<YearMonth, Long> userCounts = users.stream()
                .filter(user -> user.getRegistrationDate() != null) // Filter out users with null registration dates
                .collect(Collectors.groupingBy(
                        user -> {
                            Instant instant = user.getRegistrationDate().toInstant();
                            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                            return YearMonth.from(localDateTime); // Group by registration month
                        },
                        Collectors.counting() // Count the users for each month
                ));

        // Convert the map to a list of UserCount objects and return it
        return userCounts.entrySet().stream()
                .map(entry -> new UserCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Long getNewUserCount() {
        // Get all users
        List<User> users = userDao.findAll();

        // Get the current date (without time)
        LocalDate today = LocalDate.now();

        // Filter the users registered today and return the count
        return users.stream()
                .filter(user -> user.getRegistrationDate() != null) // Filter out users with null registration dates
                .filter(user -> user.getRegistrationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(today))
                .count();
    }


public Long getTotalUserCount() {
    // Get all users
    List<User> users = userDao.findAll();

    // Filter out admin users
    users = users.stream()
            .filter(user -> user.getRole().stream().noneMatch(role -> role.getRolename().equals("Admin")))
            .collect(Collectors.toList());

    // Return the total number of non-admin users
    return (long) users.size();
}


    public List<User> getAllUsers(String searchKeyword, String statusFilter) {
        // Get all users
        List<User> users = userDao.findAll();

        // Filter out admin users
        users = users.stream()
                .filter(user -> user.getRole().stream().noneMatch(role -> role.getRolename().equals("Admin")))
                .collect(Collectors.toList());

        // Filter users based on the status filter
        if (statusFilter != null) {
            switch (statusFilter.toLowerCase()) {
                case "verified":
                    users = users.stream()
                            .filter(User::getEnabled)
                            .collect(Collectors.toList());
                    break;
                case "unverified":
                    users = users.stream()
                            .filter(user -> !user.getEnabled())
                            .collect(Collectors.toList());
                    break;
                case "all":
                default:
                    // Do nothing, keep all users
                    break;
            }
        }

        // Filter users based on the search keyword
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            String keyword = searchKeyword.toLowerCase();
            users = users.stream()
                    .filter(user ->
                            user.getUserName().toLowerCase().contains(keyword) ||
                                    user.getUserFirstName().toLowerCase().contains(keyword) ||
                                    user.getUserLastname().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
        }

        return users;
    }


//    public List<User> getAllUsers(String searchKeyword) {
//        // Get all users
//        List<User> users = userDao.findAll();
//
//        // Filter out admin users
//        users = users.stream()
//                .filter(user -> user.getRole().stream().noneMatch(role -> role.getRolename().equals("Admin")))
//                .collect(Collectors.toList());
//
//        // Filter users based on the search keyword
//        if (searchKeyword != null && !searchKeyword.isEmpty()) {
//            String keyword = searchKeyword.toLowerCase();
//            users = users.stream()
//                    .filter(user ->
//                            user.getUserName().toLowerCase().contains(keyword) ||
//                                    user.getUserFirstName().toLowerCase().contains(keyword) ||
//                                    user.getUserLastname().toLowerCase().contains(keyword))
//                    .collect(Collectors.toList());
//        }
//
//        return users;
//    }






}

