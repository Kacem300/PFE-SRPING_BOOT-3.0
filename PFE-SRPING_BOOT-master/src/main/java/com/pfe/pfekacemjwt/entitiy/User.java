package com.pfe.pfekacemjwt.entitiy;


import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id

    private String userName;
    //    private String userId;
    private String userEmail;
    private String userFirstName;
    private String userLastname;
    private String userPassword;
    private Date RegistrationDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name="USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name="ROLE_ID")
            }
    )
   private Set<Role> role;



    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userImage")
    private imageModel userImage;

    private Boolean enabled;

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        userId = userId;
//    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(name = "userImage")
//    private imageModel userImage;



    public imageModel getUserImage() {
        return userImage;
    }

    public void setUserImage(imageModel userImage) {
        this.userImage = userImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Set<Role> getRole() {
        return role;
    }

    public void setRole(Set<Role> role) {
        this.role = role;
    }

    public Date getRegistrationDate() {
        return RegistrationDate;
    }

    public void setRegistrationDate(Date orderDate) {
        this.RegistrationDate = orderDate;
    }
}
