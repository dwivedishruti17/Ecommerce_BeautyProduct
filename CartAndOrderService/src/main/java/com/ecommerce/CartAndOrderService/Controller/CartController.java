package com.ecommerce.CartAndOrderService.Controller;


import com.ecommerce.CartAndOrderService.Dto.CartDto;
import com.ecommerce.CartAndOrderService.Entity.Cart;
import com.ecommerce.CartAndOrderService.Entity.CartItem;
import com.ecommerce.CartAndOrderService.Exceptions.NotFoundException;
import com.ecommerce.CartAndOrderService.Security.JwtValidator;
import com.ecommerce.CartAndOrderService.Service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.GeneralSecurityException;
import java.util.Optional;


@CrossOrigin(origins = {"http://localhost:3001", "http://localhost:3000", "http://localhost:3002"})
@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private JwtValidator jwtValidator;

    @PostMapping()
    public ResponseEntity<?> addTocart(@Valid  @RequestBody CartItem cartItem, HttpServletRequest request ) throws GeneralSecurityException {

        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        System.out.println("loggedddd INNN USerrrrrr : "+ loggedInuserId);
            CartDto cart = cartService.addtoCart(loggedInuserId, cartItem);
            return ResponseEntity.ok().body(cart);

    }

    @GetMapping()
    public ResponseEntity<?> getUserCart( HttpServletRequest request)
    {
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        System.out.println("loggedddd INNN USerrrrrr : "+ loggedInuserId);
            Optional<CartDto> cart = cartService.findCartById(loggedInuserId);
            if (cart.isEmpty()) {
                throw new NotFoundException("cart is empty");
            }
            return ResponseEntity.ok().body(cart);
    }

    @PatchMapping()
    public ResponseEntity<?> updateCart(@Valid @RequestBody CartItem cartItem,HttpServletRequest request ){
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        System.out.println("loggedddd INNN USerrrrrr : "+ loggedInuserId);
            CartDto cart = cartService.updateCartItem(loggedInuserId, cartItem);
            return ResponseEntity.ok().body(cart);

    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteCartItem( @PathVariable Long productId,  HttpServletRequest request){
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        System.out.println("loggedddd INNN USerrrrrr : "+ loggedInuserId);
            CartDto cart=  cartService.deleteCartItem(loggedInuserId, productId);
            return ResponseEntity.ok().body(cart);
    }
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart(HttpServletRequest request){
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        System.out.println("loggedddd INNN USerrrrrr : "+ loggedInuserId);
             Optional<CartDto> cart = cartService.deleteCart(loggedInuserId);
             return ResponseEntity.ok().body(cart);

    }

    @PostMapping("/wishlist")
    public ResponseEntity<?> addToWishList(@Valid  @RequestBody CartItem cartItem, HttpServletRequest request ) throws GeneralSecurityException {
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        System.out.println("loggedddd INNN USerrrrrr : "+ loggedInuserId);
        CartDto cart = cartService.addtoWishlist(loggedInuserId, cartItem);
        return ResponseEntity.ok().body(cart);
    }

    @DeleteMapping("/wishlist/product/{productId}")
    public ResponseEntity<?> deleteWishListItem( @PathVariable Long productId,  HttpServletRequest request){
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        System.out.println("loggedddd INNN USerrrrrr : "+ loggedInuserId);
        CartDto wishlist=  cartService.deleteWishListItem(loggedInuserId, productId);
        return ResponseEntity.ok().body(wishlist);
    }

    @GetMapping("/wishlist")
    public ResponseEntity<?> getUserWishList( HttpServletRequest request)
    {
        Long loggedInuserId = jwtValidator.getuserIdFromToken(request);
        System.out.println("loggedddd INNN USerrrrrr : "+ loggedInuserId);
        Optional<CartDto> wishlist = cartService.findWishListById(loggedInuserId);
        if (wishlist.isEmpty()) {
            throw new NotFoundException("cart is empty");
        }
        return ResponseEntity.ok().body(wishlist);
    }


}
