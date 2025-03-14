package com.ecommerce.CartAndOrderService.Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BuyNowRequestDto {

    @NotNull(message = "productId is mandatory field")
    private Long productId;

    @NotNull(message = "AddressId is mandatory field")
    private Long addressId;

    @Min(value = 1, message = "quantity must be greater than 0")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
