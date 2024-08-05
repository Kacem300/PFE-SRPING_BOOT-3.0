package com.pfe.pfekacemjwt.dao;

import com.pfe.pfekacemjwt.entitiy.OrderDetail;
import com.pfe.pfekacemjwt.entitiy.Product;
import com.pfe.pfekacemjwt.entitiy.Rating;
import com.pfe.pfekacemjwt.entitiy.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDao extends CrudRepository<OrderDetail, Integer>{
    public List<OrderDetail> findByUser(User user);
    public List<OrderDetail> findByOrderStatus(String status);
    public List<OrderDetail> findByOrderStatusNot(String status);
    public List<OrderDetail> findByProduct_ProductId(Integer productId);
    List<OrderDetail> findByProduct(Product product);


    Optional<OrderDetail> findByOrderFullNameContainingIgnoreCaseOrUser_UserNameContainingIgnoreCase(String OrderFullName,String UserName);
    Optional<OrderDetail> findByProduct_ProductNameContainingIgnoreCase(String ProductName);

}
