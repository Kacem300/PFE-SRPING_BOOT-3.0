package com.pfe.pfekacemjwt.service;

import com.pfe.pfekacemjwt.configuration.jwtRequestFilter;
import com.pfe.pfekacemjwt.dao.CartDao;
import com.pfe.pfekacemjwt.dao.ProductDao;
import com.pfe.pfekacemjwt.dao.UserDao;
import com.pfe.pfekacemjwt.entitiy.Cart;
import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartDao cartDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    //    public Cart addToCart(Integer productId) {
//        Product product = productDao.findById(productId).get();
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        User user = null;
//        if(username != null) {
//            user = userDao.findById(username).get();
//        }
//
//        if(product != null && user != null) {
//            Cart cart = new Cart(product, user);
//            return cartDao.save(cart);
//        }
//
//        return null;
//    }
    public List<Cart> addToCart(List<Integer> productIds) {
        List<Cart> carts = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = null;
        if(username != null) {
            user = userDao.findById(username).get();
        }
        // Delete existing cart of the user
        if(user != null) {
            cartDao.deleteByUser(user);
        }

        for(Integer productId : productIds) {
            Product product = productDao.findById(productId).get();
            if(product != null && user != null) {
                Cart cart = new Cart(product, user);
                carts.add(cartDao.save(cart));
            }
        }

        return carts;
    }





    public List<Cart> getCart() {
        String username =jwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(username).get();
        return cartDao.findByUser(user);

    }

}
