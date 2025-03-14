package com.ecommerce.CartAndOrderService.Dto;

import com.ecommerce.CartAndOrderService.Enums.OrderStatus;

public class OrderStatusRequestDto {
    private OrderStatus orderStatus;

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
