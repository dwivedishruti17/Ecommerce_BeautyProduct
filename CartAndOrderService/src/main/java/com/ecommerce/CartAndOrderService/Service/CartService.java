package com.ecommerce.CartAndOrderService.Service;

import com.ecommerce.CartAndOrderService.Entity.Cart;
import com.ecommerce.CartAndOrderService.Entity.CartItem;
import com.ecommerce.CartAndOrderService.Exceptions.ProductNotFoundException;
import com.ecommerce.CartAndOrderService.Exceptions.ProductOutOfStockException;
import com.ecommerce.CartAndOrderService.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductServiceClient productServiceClient;




public Cart addtoCart(Long userId, CartItem cartItem){
    try {
        productServiceClient.getProductById(cartItem.getProductId());
    } catch (Exception e) {
        throw new ProductNotFoundException("Product with ID " + cartItem.getProductId() + " does not exist.");
    }
    if(cartItem.getQuantity()> productServiceClient.getProductById(cartItem.getProductId()).getQuantity()){
        throw new ProductOutOfStockException("Product is out of stock");
    }
        Cart cart = cartRepository.findCartByUserId(userId).orElse(new Cart(null, userId, new ArrayList<>()));
//        Optional<CartItem> exits = cart.getItems().stream()
//                .filter(item -> item.getProductId().equals(cartItem.getProductId())).findFirst();
//        if(exits.isPresent()){
//            if(cartItem.getQuantity()> productServiceClient.getProductById(cartItem.getProductId()).getQuantity()) {
//                exits.get().setQuantity(exits.get().getQuantity() + cartItem.getQuantity());
//            }
//        }
    Optional<CartItem> exists = cart.getItems().stream()
            .filter(item -> item.getProductId().equals(cartItem.getProductId())).findFirst();
    if (exists.isPresent()) {
        int newQuantity = exists.get().getQuantity() + cartItem.getQuantity();
        // Check if the new quantity exceeds the available stock
        if (newQuantity > productServiceClient.getProductById(cartItem.getProductId()).getQuantity()) {
            throw new ProductOutOfStockException("Product with ID " + cartItem.getProductId() + " is out of stock.");
        }
        exists.get().setQuantity(newQuantity);
    }
    else{
            cart.getItems().add(cartItem);
        }

        return cartRepository.save(cart);
}
    public Optional<Cart> findCartById(Long userId){
        return Optional.ofNullable(cartRepository.findCartByUserId(userId).orElseThrow(() -> new RuntimeException("user id not found")));
    }

    public Cart getCartByUserId(Long userId){
    return cartRepository.findCartByUserId(userId).orElseThrow(()->new RuntimeException("cart not found"));
    }

    public Cart updateCartItem(Long userId, CartItem cartItem){
    Cart cart = getCartByUserId(userId);

        cart.getItems().forEach(item-> {
            if (item.getProductId().equals(cartItem.getProductId())) {
                if(item.getQuantity()>productServiceClient.getProductById(cartItem.getProductId()).getQuantity()){
                    throw new ProductOutOfStockException("Product is out of stock");
                }
                else {
                    item.setQuantity(cartItem.getQuantity());
                }
            }
        });

    return cartRepository.save(cart);

    }
    public Cart deleteCartItem(Long userId, Long productId){
      Cart cart = getCartByUserId(userId);
      cart.getItems().removeIf(item -> item.getProductId().equals(productId));
      return cartRepository.save(cart);
    }

    public Optional<Cart> deleteCart(Long userId){
       return cartRepository.deleteCartByUserId(userId);
    }

}
