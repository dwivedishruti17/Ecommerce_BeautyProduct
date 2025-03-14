package com.ecommerce.CartAndOrderService.Controller;

import com.ecommerce.CartAndOrderService.Dto.AddressRequestDto;
import com.ecommerce.CartAndOrderService.Dto.BuyNowRequestDto;
import com.ecommerce.CartAndOrderService.Dto.OrderDto;
import com.ecommerce.CartAndOrderService.Dto.OrderStatusRequestDto;
import com.ecommerce.CartAndOrderService.Entity.Order;
import com.ecommerce.CartAndOrderService.Enums.OrderStatus;
import com.ecommerce.CartAndOrderService.Security.JwtValidator;
import com.ecommerce.CartAndOrderService.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000", "http://localhost:3002"})
@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtValidator jwtValidator;
    @PostMapping()
    public OrderDto createOrder(HttpServletRequest request, @RequestBody AddressRequestDto addressRequestDto){
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        String token = request.getHeader("Authorization");

        return orderService.createOrder(loggedInuserId, addressRequestDto.getAddressId(), token);
    }
    @GetMapping()
    public List<OrderDto> getUserOrder(HttpServletRequest request ){
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        return orderService.getUserOrder(loggedInuserId);
    }

    @PutMapping("{orderId}/status")
    public OrderDto updateOrderStatus(@PathVariable String orderId, @RequestBody OrderStatusRequestDto orderStatus){
//        String role = jwtValidator.extractRole(request);
//        System.out.println("roleee"+ role);
//        if(orderStatus.getOrderStatus()== OrderStatus.CANCELLED || "ROLE_ADMIN".equals(role)) {
            return orderService.updateOrderStatus(orderId, orderStatus.getOrderStatus());
//        }
//        else{
//            throw new RuntimeException("Only admin can set the status to: "+ orderStatus.getOrderStatus());
//        }
    }

    @PostMapping("/buyNow")
    public OrderDto buyNow(HttpServletRequest request, @Valid @RequestBody BuyNowRequestDto buyNowRequestDto){
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        String token = request.getHeader("Authorization");
        return orderService.buyNow(loggedInuserId, buyNowRequestDto, token);
    }

}
