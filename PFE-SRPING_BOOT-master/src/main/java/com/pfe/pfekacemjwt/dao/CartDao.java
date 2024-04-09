package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.Cart;
import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartDao extends CrudRepository<Cart, Integer> {
    public List<Cart> findByUser(User user);
    @Transactional
    void deleteByUser(User user);
}

