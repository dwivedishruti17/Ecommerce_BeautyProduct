package com.ecommerce.CartAndOrderService.Service;

import com.ecommerce.CartAndOrderService.Dto.Address;
import com.ecommerce.CartAndOrderService.Dto.BuyNowRequestDto;
import com.ecommerce.CartAndOrderService.Dto.OrderDto;
import com.ecommerce.CartAndOrderService.Dto.ProductDto;
import com.ecommerce.CartAndOrderService.Entity.Cart;
import com.ecommerce.CartAndOrderService.Entity.CartItem;
import com.ecommerce.CartAndOrderService.Entity.Order;
import com.ecommerce.CartAndOrderService.Entity.OrderItem;
import com.ecommerce.CartAndOrderService.Enums.OrderStatus;
import com.ecommerce.CartAndOrderService.Exceptions.NotFoundException;
import com.ecommerce.CartAndOrderService.Exceptions.ProductNotFoundException;
import com.ecommerce.CartAndOrderService.repository.CartRepository;
import com.ecommerce.CartAndOrderService.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
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
    private UserServiceClient userServiceClient;

    @Autowired
    private ProductServiceClient productServiceClient;
    public OrderDto createOrder(Long userId, Long addressId, String token){
        Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(()-> new NotFoundException("cart not found for user"));
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
            System.out.println("quantityyy beforeee : "+ quantity);
            Integer newquantity = quantity - cartItem.getQuantity();
            productDto.setQuantity(newquantity);
            System.out.println("quantityyy afterrrr : "+ productDto.getQuantity());
            productServiceClient.updateProductQuantity(cartItem.getProductId(), newquantity);
        }
        double totalAmount = orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        if (userId == null) {
            throw new RuntimeException("User ID is null, order cannot be created.");
        }
        Address address = userServiceClient.getAddressByUserIdAndAddressId(userId, addressId, token);
//        address.setUserId(userId);
        System.out.println("Addresss : "+ address);
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setAddress(address);
        Order savedOrder = orderRepository.save(order);
        cartRepository.delete(cart);
        System.out.println("Order Created Successfully: " + savedOrder);
        OrderDto orderDto = convertToDto(savedOrder);
        System.out.println("Orderr DToooo"+ orderDto);

        return orderDto;

    }
    public OrderDto buyNow(Long userId, BuyNowRequestDto buyNowRequestDto,  String token){
        try {
            productServiceClient.getProductById(buyNowRequestDto.getProductId());
        } catch (Exception e) {
            throw new ProductNotFoundException("Product with ID " + buyNowRequestDto.getProductId() + " does not exist.");
        }
        ProductDto productDto = productServiceClient.getProductById(buyNowRequestDto.getProductId());
        Double productPrice = productDto.getPrice();
        String productName = productDto.getName();
        String description = productDto.getDescription();
        Integer quantity = productDto.getQuantity();
        List<OrderItem> orderItems = new ArrayList<>();

        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(productPrice);
        orderItem.setProductId(buyNowRequestDto.getProductId());
        orderItem.setQuantity(buyNowRequestDto.getQuantity());
        orderItem.setDescription(description);
        orderItem.setName(productName);
        orderItems.add(orderItem);
        Integer newquantity = quantity - buyNowRequestDto.getQuantity();
        productDto.setQuantity(newquantity);
        System.out.println("quantityyy afterrrr : "+ productDto.getQuantity());
        productServiceClient.updateProductQuantity(buyNowRequestDto.getProductId(), newquantity);
        double totalAmount = productPrice* buyNowRequestDto.getQuantity();
        Address address = userServiceClient.getAddressByUserIdAndAddressId(userId, buyNowRequestDto.getAddressId(), token);
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setAddress(address);
        Order savedOrder = orderRepository.save(order);
        OrderDto orderDto = convertToDto(savedOrder);
        System.out.println("Orderr DToooo"+ orderDto);
        return orderDto;

    }
    public List<OrderDto> getUserOrder(Long userId){

        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(order -> convertToDto(order))
                .collect(Collectors.toList());
    }

    public OrderDto updateOrderStatus(String orderId, OrderStatus orderStatus){
        Optional<Order> optionalorder = orderRepository.findById(orderId);
        if(optionalorder.isPresent()){
            Order order = optionalorder.get();
            if(orderStatus == OrderStatus.CANCELLED){
                updateProductQuantities(order);

            }
            order.setStatus(orderStatus);
            Order savedOrder =  orderRepository.save(order);
            return convertToDto(savedOrder);
        }
        else{
            throw new NotFoundException("Order not found with id : "+ orderId);
        }
    }

    private void updateProductQuantities(Order order) {
        for (OrderItem item : order.getItems()) {
            Optional<ProductDto> optionalProduct = Optional.ofNullable(productServiceClient.getProductById(item.getProductId()));
            if (optionalProduct.isPresent()) {
                ProductDto product = optionalProduct.get();
                int newQuantity = product.getQuantity() + item.getQuantity();
                productServiceClient.updateProductQuantity(item.getProductId(), newQuantity);

            }
        }
    }



    private OrderDto convertToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setAddress(order.getAddress());

        dto.setItems(order.getItems());
        return dto;
    }





}
