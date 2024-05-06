package com.pfe.pfekacemjwt.controller;

import com.pfe.pfekacemjwt.dao.UserDao;
import com.pfe.pfekacemjwt.entitiy.Comment;
import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.User;
import com.pfe.pfekacemjwt.service.CommentService;
import com.pfe.pfekacemjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;
    @PostMapping("/saveComment")
    public Comment saveComment(@RequestParam Integer productId, @RequestParam String commentText) {
        return commentService.saveComment(productId, commentText);
    }

    @GetMapping("/getAllComments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }


    @GetMapping("/getCommentsForProduct")
    public List<Comment> getCommentsForProduct(@RequestParam Integer productId) {
        return commentService.getCommentsForProduct(productId);
    }

}

