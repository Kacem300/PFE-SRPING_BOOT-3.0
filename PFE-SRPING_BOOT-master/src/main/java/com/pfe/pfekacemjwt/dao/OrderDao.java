package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.OrderDetail;
import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderDao extends CrudRepository<OrderDetail, Integer>{
    public List<OrderDetail> findByUser(User user);
    public List<OrderDetail> findByOrderStatus(String status);
    public List<OrderDetail> findByOrderStatusNot(String status);
    public List<OrderDetail> findByProduct_ProductId(Integer productId);



}
