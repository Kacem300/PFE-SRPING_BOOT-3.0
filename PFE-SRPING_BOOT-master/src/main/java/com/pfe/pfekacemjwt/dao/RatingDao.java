package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.Rating;
import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RatingDao extends JpaRepository<Rating, Integer> {
    List<Rating> findByProduct(Product product);
    Rating findByUserAndProduct(User user, Product product);

}




