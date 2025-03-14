package com.ecommerce.CartAndOrderService.Service;

import com.ecommerce.CartAndOrderService.Dto.CartDto;
import com.ecommerce.CartAndOrderService.Dto.ProductDto;
import com.ecommerce.CartAndOrderService.Entity.Cart;
import com.ecommerce.CartAndOrderService.Entity.CartItem;
import com.ecommerce.CartAndOrderService.Exceptions.ProductNotFoundException;
import com.ecommerce.CartAndOrderService.Exceptions.ProductOutOfStockException;
import com.ecommerce.CartAndOrderService.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductServiceClient productServiceClient;




public CartDto addtoCart(Long userId, CartItem cartItem){
    try {
      ProductDto product =  productServiceClient.getProductById(cartItem.getProductId());
      cartItem.setDescription(product.getDescription());
      cartItem.setName(product.getName());
      cartItem.setImageUrl(product.getImageUrl());
      cartItem.setPrice(product.getPrice());
    } catch (Exception e) {
        throw new ProductNotFoundException("Product with ID " + cartItem.getProductId() + " does not exist.");
    }
    if(cartItem.getQuantity()> productServiceClient.getProductById(cartItem.getProductId()).getQuantity()){
        throw new ProductOutOfStockException("Product is out of stock");
    }
        Cart cart = cartRepository.findCartByUserId(userId).orElse(new Cart(null,  userId, new ArrayList<>()));
    Optional<CartItem> exists = cart.getItems().stream()
            .filter(item -> item.getProductId().equals(cartItem.getProductId())).findFirst();
    if (exists.isPresent()) {
        int newQuantity = exists.get().getQuantity() + cartItem.getQuantity();
        if (newQuantity > productServiceClient.getProductById(cartItem.getProductId()).getQuantity()) {
            throw new ProductOutOfStockException("Product with ID " + cartItem.getProductId() + " is out of stock.");
        }
        exists.get().setQuantity(newQuantity);
    }
    else{
            cart.getItems().add(cartItem);
        }

       Cart savedcart = cartRepository.save(cart);
//        savedcart.setUserId(userId);
    System.out.println("savedcart : "+ savedcart);
    return convertToDto(savedcart);
}


    public Optional<CartDto> findCartById(Long userId){
    Optional<CartDto> userCart =   cartRepository.findCartByUserId(userId).map(this::convertToDto);
        System.out.println("user cartttt: "+ userCart);
    return userCart;

    }

    public CartDto getCartByUserId(Long userId){

    return cartRepository.findCartByUserId(userId).map(this::convertToDto).orElseThrow(()->new RuntimeException("cart not found"));
    }

    public CartDto updateCartItem(Long userId, CartItem cartItem){
    Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(()-> new RuntimeException("cart not found"));
        try{
            productServiceClient.getProductById(cartItem.getProductId());
        }
        catch (Exception e){
            throw new ProductNotFoundException("product with id :"+cartItem.getProductId()+" does not exist");
        }
        boolean itemExistsInCart = cart.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(cartItem.getProductId()));
        if (!itemExistsInCart) {
            throw new ProductNotFoundException("Product with id: " + cartItem.getProductId() + " is not found in your cart");
        }
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

    Cart savedcart = cartRepository.save(cart);
    return convertToDto(savedcart);
    }
    public CartDto deleteCartItem(Long userId, Long productId){
        try{
            productServiceClient.getProductById(productId);
        }
        catch (Exception e){
            throw new ProductNotFoundException("product with id :"+productId+" does not exist");
        }
      Cart cart = cartRepository.findCartByUserId(userId).orElseThrow(()-> new RuntimeException("cart not found"));
        boolean itemExistsInCart = cart.getItems().stream()
                .anyMatch(item -> item.getProductId().equals(productId));
        if (!itemExistsInCart) {
            throw new ProductNotFoundException("Product with id: " + productId + " is not found in your cart");
        }
      cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        Cart savedCart = cartRepository.save(cart);
        return convertToDto(savedCart);
    }

    public Optional<CartDto> deleteCart(Long userId){
       Optional<Cart> cart =  cartRepository.deleteCartByUserId(userId);
       return cart.map(this::convertToDto);
    }

    private CartDto convertToDto(Cart cart) {
        return new CartDto(
                cart.getId(),
                cart.getItems()

        );
    }



}
