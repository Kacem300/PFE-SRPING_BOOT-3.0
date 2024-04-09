package com.pfe.pfekacemjwt.service;


import com.pfe.pfekacemjwt.dao.CartDao;
import com.pfe.pfekacemjwt.dao.OrderDao;
import com.pfe.pfekacemjwt.dao.ProductDao;
import com.pfe.pfekacemjwt.dao.UserDao;
import com.pfe.pfekacemjwt.entitiy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service

public class OrderDetailsService {

    private static final String Order_status = "Placed";
    @Autowired
    private ProductDao productDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CartDao cartDao;




    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderQuantity> productQuantityList = orderInput.getOrderQuantities();

        for (OrderQuantity o: productQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUser = authentication.getName();
            User user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    Order_status,
                    product.getProductDiscountprice() * o.getQuantity(),
                    product,
                    user
//                    orderInput.getTransactionId()
            );
            orderDetail.setOrderDate(new Date());

            // empty the cart.
            if(!isSingleProductCheckout) {
                List<Cart> carts = cartDao.findByUser(user);
                carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
            }

            orderDao.save(orderDetail);
        }
    }

    public List<OrderDetail> getOrderDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = authentication.getName();
        User user = userDao.findById(currentUser).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return orderDao.findByUser(user);
    }




    public List<OrderDetail> getAllOrderDetails(String status) {

        List<OrderDetail> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
            orderDao.findAll().forEach(
                    x -> orderDetails.add(x)
            );
        } else {
            orderDao.findByOrderStatus(status).forEach(
                    x -> orderDetails.add(x)
            );
        }


        return orderDetails;
    }

    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDao.findById(orderId).get();

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDao.save(orderDetail);
        }

    }
    public void markOrderAsPlaced(Integer orderId) {
        OrderDetail orderDetail = orderDao.findById(orderId).get();

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Placed");
            orderDao.save(orderDetail);
        }

    }

    public List<OrderCount> getOrderCountsPerMonth() {
        // Get all orders
        List<OrderDetail> orders = getAllOrderDetails("All");

        // Create a map to store the count of orders per month
        Map<YearMonth, Long> orderCounts = orders.stream()
                .filter(order -> order.getOrderDate() != null) // Filter out orders with null dates
                .collect(Collectors.groupingBy(
                        order -> {
                            Instant instant = order.getOrderDate().toInstant();
                            LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                            return YearMonth.from(localDateTime); // Group by order month
                        },
                        Collectors.counting() // Count the orders for each month
                ));

        // Convert the map to a list of OrderCount objects and return it
        return orderCounts.entrySet().stream()
                .map(entry -> new OrderCount(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }


    public Long getTotalOrderCount() {
        // Get all orders
        List<OrderDetail> orders = getAllOrderDetails("All");

        // Return the total number of orders
        return (long) orders.size();
    }

    public Long getNewOrderCount() {
        // Get all orders
        List<OrderDetail> orders = getAllOrderDetails("All");

        // Get the current date (without time)
        LocalDate today = LocalDate.now();

        // Filter the orders placed today and return the count
        return orders.stream()
                .filter(order -> order.getOrderDate() != null) // Add this line to filter out orders with null dates
                .filter(order -> order.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(today))
                .count();
    }




}




