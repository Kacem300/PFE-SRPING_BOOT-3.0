package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserDao extends JpaRepository<User,String> {
    Optional<User> findByUserNameOrUserEmail(String userName, String userEmail);
}
