package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.Comment;
import com.pfe.pfekacemjwt.entitiy.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {
    List<Comment> findByProduct(Product product);
}
