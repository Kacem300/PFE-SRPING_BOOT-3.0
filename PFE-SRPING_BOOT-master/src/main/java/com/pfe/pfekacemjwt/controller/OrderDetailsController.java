package com.pfe.pfekacemjwt.controller;

import com.pfe.pfekacemjwt.entitiy.OrderCount;
import com.pfe.pfekacemjwt.entitiy.OrderDetail;
import com.pfe.pfekacemjwt.entitiy.OrderInput;
import com.pfe.pfekacemjwt.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;
@RestController
public class OrderDetailsController {

@Autowired
private OrderDetailsService orderDetailsService;

    @PostMapping("/placeOrder/{single}")
//      @PreAuthorize("hasRole('User')")
    private void placeOrder(@PathVariable(name = "single") boolean single,
                            @RequestBody OrderInput orderInput) {
        orderDetailsService.placeOrder(orderInput, single);
    }
    @GetMapping({"/getOrderDetails"})
//    @PreAuthorize("hasRole('User')")
    public List<OrderDetail> getOrderDetails() {
        return orderDetailsService.getOrderDetails();
    }



    @GetMapping({"/getAllOrderDetails/{status}"})
//   @PreAuthorize("hasRole('Admin')")
    public List<OrderDetail> getAllOrderDetails(@PathVariable(name = "status") String status) {
        return orderDetailsService.getAllOrderDetails(status);
    }

    @GetMapping({"/markOrderAsDelivered/{orderId}"})
//    @PreAuthorize("hasRole('Admin')")
    public void markOrderAsDelivered(@PathVariable(name = "orderId") Integer orderId) {
        orderDetailsService.markOrderAsDelivered(orderId);
    }
    @GetMapping({"/markOrderAsPlaced/{orderId}"})
//    @PreAuthorize("hasRole('Admin')")
    public void markOrderAsPlaced(@PathVariable(name = "orderId") Integer orderId) {
        orderDetailsService.markOrderAsPlaced(orderId);
    }
    @GetMapping("/getOrderCountsPerMonth")
    public List<OrderCount> getOrderCountsPerMonth() {
        return orderDetailsService.getOrderCountsPerMonth();
    }

    @GetMapping("/getTotalOrderCount")
    public Long getTotalOrderCount() {
        return orderDetailsService.getTotalOrderCount();
    }

    @GetMapping("/getNewOrderCount")
    public Long getNewOrderCount() {
        return orderDetailsService.getNewOrderCount();
    }

}
