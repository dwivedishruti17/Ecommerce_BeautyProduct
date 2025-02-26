package com.ecommerce.CartAndOrderService.Entity;


import com.ecommerce.CartAndOrderService.Enums.OrderStatus;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Document(collection = "order")
@Data
public class Order {
    @Id
    private String id;
    private Long userId;
    private List<OrderItem>items;

    private double totalAmount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    private Date orderDate;
    private OrderStatus status; //make enum PENDING, COMPLETED, CANCELLED


//    public Order(Long userId, List<OrderItem> orderItems, Double totalAmount, Date date, OrderStatus pending) {
//    }
}
