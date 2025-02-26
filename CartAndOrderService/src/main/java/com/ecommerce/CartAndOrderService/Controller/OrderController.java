package com.ecommerce.CartAndOrderService.Controller;

import com.ecommerce.CartAndOrderService.Entity.Order;
import com.ecommerce.CartAndOrderService.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/{userId}")
    public Order createOrder(@PathVariable Long userId){
        return orderService.createOrder(userId);
    }
    @GetMapping("/{userId}")
    public List<Order> getUserOrder(@PathVariable Long userId){
        return orderService.getUserOrder(userId);
    }
}
