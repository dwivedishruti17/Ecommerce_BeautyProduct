package com.ecommerce.CartAndOrderService.Controller;


import com.ecommerce.CartAndOrderService.Entity.Cart;
import com.ecommerce.CartAndOrderService.Entity.CartItem;
import com.ecommerce.CartAndOrderService.Service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping("mycart")
    public String getmycart(){
        return "these are my cart item";
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Cart> addTocart(@PathVariable Long userId, @RequestBody CartItem cartItem){
      Cart cart=  cartService.addtoCart(userId, cartItem);
      return ResponseEntity.ok().body(cart);

    }

    @GetMapping("/user/{userId}")
    public Optional<Cart> getUserCart(@PathVariable Long userId)
    {
        return cartService.findCartById(userId);
    }

    @PatchMapping("/user/{userId}")
    public Cart updateCart(@PathVariable Long userId, @RequestBody CartItem cartItem){
        return cartService.updateCartItem(userId, cartItem);
    }

    @DeleteMapping("user/{userId}")
    public Cart deleteCartItem(@PathVariable Long userId, @RequestBody CartItem cartItem){
        return cartService.deleteCartItem(userId, cartItem.getProductId());
    }
    @DeleteMapping("user/{userId}/cart")
    public Optional<Cart> clearCart(@PathVariable Long userId){
        return cartService.deleteCart(userId);
    }

}
