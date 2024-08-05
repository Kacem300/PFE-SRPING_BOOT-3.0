package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User,String> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_role WHERE user_id = :userId", nativeQuery = true)
    void deleteUserRoles(@Param("userId") String userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM comment WHERE user_user_name = :userName", nativeQuery = true)
    void deleteUserComments(@Param("userName") String userName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM verification_token WHERE user_user_name = :userName", nativeQuery = true)
    void deleteUserVerificationTokens(@Param("userName") String userName);

    Optional<User> findByUserNameOrUserEmail(String userName, String userEmail);
    User findByUserEmail(String email);

}
