package com.pfe.pfekacemjwt.controller;

import com.pfe.pfekacemjwt.entitiy.Cart;
import com.pfe.pfekacemjwt.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    @Autowired
    private CartService cartService;
//    @PreAuthorize("hasRole('User')")
//   @GetMapping("/addToCart/{productId}")
//    public Cart addToCart(@PathVariable(name = "productId") Integer productId){
//      return  cartService.addToCart(productId);
//    }

    @PreAuthorize("hasRole('User')")
    @PostMapping("/addToCart")
    public List<Cart> addToCart(@RequestBody List<Integer> productIds){
        return cartService.addToCart(productIds);
    }


    @PreAuthorize("hasRole('User')")
    @GetMapping("/getCart")
    public List<Cart> getCart(){
        return cartService.getCart();
    }
}
