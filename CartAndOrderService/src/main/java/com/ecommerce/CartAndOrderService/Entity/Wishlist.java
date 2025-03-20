package com.ecommerce.CartAndOrderService.Entity;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "wishlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {
    @Id
    private String id;
    private Long userId;

    @Valid
    private List<CartItem> items;
}
