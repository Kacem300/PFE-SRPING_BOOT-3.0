package com.pfe.pfekacemjwt.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Entity

public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer OrderId;
    private String orderFullName;
    private String OrderFullOrder;
    private String orderContactNumber;
    private Integer orderQuantity;
    private String orderStatus;
    private Double orderAmount;


    private Date orderDate;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Product product;
    @ManyToOne
    private User user;



    public OrderDetail(String orderFullName, String orderFullOrder, String orderContactNumber, Integer  orderQuantity , String orderStatus, Double orderAmount, Product product, User user) {
        this.orderFullName = orderFullName;
        OrderFullOrder = orderFullOrder;
        this.orderContactNumber = orderContactNumber;
        this.orderQuantity = orderQuantity;
        this.orderStatus = orderStatus;
        this.orderAmount = orderAmount;
        this.product = product;
        this.user = user;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getOrderId() {
        return OrderId;
    }

    public void setOrderId(Integer orderId) {
        OrderId = orderId;
    }

    public String getOrderFullName() {
        return orderFullName;
    }

    public void setOrderFullName(String orderFullName) {
        orderFullName = orderFullName;
    }

    public String getOrderFullOrder() {
        return OrderFullOrder;
    }

    public void setOrderFullOrder(String orderFullOrder) {
        OrderFullOrder = orderFullOrder;
    }

    public String getOrderContactNumber() {
        return orderContactNumber;
    }

    public void setOrderContactNumber(String orderContactNumber) {
        this.orderContactNumber = orderContactNumber;
    }

//    public String getOrderAlternateContactNumber() {
//        return orderAlternateContactNumber;
//    }
//
//    public void setOrderAlternateContactNumber(String orderAlternateContactNumber) {
//        this.orderAlternateContactNumber = orderAlternateContactNumber;
//    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderDetail() {

    }




}
