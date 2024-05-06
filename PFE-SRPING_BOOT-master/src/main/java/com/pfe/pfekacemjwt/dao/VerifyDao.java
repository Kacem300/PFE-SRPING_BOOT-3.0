package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.User;
import com.pfe.pfekacemjwt.entitiy.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerifyDao extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);
}

