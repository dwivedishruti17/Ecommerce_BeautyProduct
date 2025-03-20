package com.ecommerce.CartAndOrderService.Dto;

import com.ecommerce.CartAndOrderService.Entity.OrderItem;
import com.ecommerce.CartAndOrderService.Enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto implements Serializable {
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    private String id;
    private Long userId;

//    public OrderDto(String id, Date orderDate, List<OrderItem> items, OrderStatus status, double totalAmount) {


    public OrderDto(String id, Date orderDate, List<OrderItem> items, OrderStatus status, double totalAmount, Address address) {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    private List<OrderItem> items;
    private double totalAmount;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    private Date orderDate;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private OrderStatus orderStatus;


    private Address address;


}
