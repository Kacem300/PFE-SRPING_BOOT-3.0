package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,String> {
    Optional<User> findByUserNameOrUserEmail(String userName, String userEmail);
    User findByUserEmail(String email);

}
