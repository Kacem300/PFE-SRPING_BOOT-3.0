package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDao extends JpaRepository<Comment, Long> {
}
