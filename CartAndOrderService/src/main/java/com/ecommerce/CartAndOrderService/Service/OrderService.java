package com.ecommerce.CartAndOrderService.Service;

import com.ecommerce.CartAndOrderService.Dto.ProductDto;
import com.ecommerce.CartAndOrderService.Entity.Cart;
import com.ecommerce.CartAndOrderService.Entity.CartItem;
import com.ecommerce.CartAndOrderService.Entity.Order;
import com.ecommerce.CartAndOrderService.Entity.OrderItem;
import com.ecommerce.CartAndOrderService.Enums.OrderStatus;
import com.ecommerce.CartAndOrderService.Exceptions.ProductNotFoundException;
import com.ecommerce.CartAndOrderService.repository.CartRepository;
import com.ecommerce.CartAndOrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductServiceClient productServiceClient;
    public Order createOrder(Long userId){
        Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(()-> new RuntimeException("cart not found for user"));
        if(cart.getItems().isEmpty()){
            throw new RuntimeException("Cart is empty, please add items to your cart to place an order");
        }
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
               ProductDto productDto = productServiceClient.getProductById(cartItem.getProductId());
                System.out.println("productttt : " + productDto);
            if (productDto == null) {
                throw new RuntimeException("Failed to fetch product details for ID: " + cartItem.getProductId());
            }
            Double productPrice = productDto.getPrice();
            String productName = productDto.getName();
            String description = productDto.getDescription();
            Integer quantity = productDto.getQuantity();

            System.out.println("Fetched Product: " + productName + " | Price: " + productPrice + " | Desc: " + description);
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(productPrice);
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setDescription(description);
            orderItem.setName(productName);
            orderItems.add(orderItem);
//            orderItems.add(new OrderItem(cartItem.getProductId(), productName, productPrice, description, cartItem.getQuantity()));
        }

        double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        if (userId == null) {
            throw new RuntimeException("User ID is null, order cannot be created.");
        }
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order);

        cartRepository.delete(cart);
        System.out.println("Order Created Successfully: " + savedOrder);
        return savedOrder;

    }

    public List<Order> getUserOrder(Long userId){
      return orderRepository.findByUserId(userId);
    }






}
