package com.ecommerce.CartAndOrderService.Entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class CartItem {

   @NotNull(message = "productId is mandatory field")
   private Long productId;

//   @NotNull(message = "quantity is mandatory field")
//   @Min(value = 1 , message = "quantity must be greater than 0")
   private int quantity;


   public Long getProductId() {
      return productId;
   }

   public void setProductId(Long productId) {
      this.productId = productId;
   }

   public int getQuantity() {
      return quantity;
   }

   public String name;
   public String description;
   public String imageUrl;
   public Double price;

   public Double getPrice() {
      return price;
   }

   public void setPrice(Double price) {
      this.price = price;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getImageUrl() {
      return imageUrl;
   }

   public void setImageUrl(String imageUrl) {
      this.imageUrl = imageUrl;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }


}
