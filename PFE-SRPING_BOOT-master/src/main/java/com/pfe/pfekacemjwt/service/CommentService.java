package com.pfe.pfekacemjwt.service;

import com.pfe.pfekacemjwt.dao.CommentDao;
import com.pfe.pfekacemjwt.dao.ProductDao;
import com.pfe.pfekacemjwt.dao.UserDao;
import com.pfe.pfekacemjwt.entitiy.Comment;
import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {
 @Autowired
    private CommentDao CommentDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;


    public Comment saveComment(Integer productId, String commentText) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User user = userDao.findById(currentUser).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productDao.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setProduct(product);
        comment.setCommentText(commentText);
        comment.setCommentDate(new Date());
        return CommentDao.save(comment);
    }




    public List<Comment> getAllComments() {
        return CommentDao.findAll();
    }
}
