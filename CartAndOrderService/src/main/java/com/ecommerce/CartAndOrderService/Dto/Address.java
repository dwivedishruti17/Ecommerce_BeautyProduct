package com.ecommerce.CartAndOrderService.Dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String street) {
        this.state = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }

    @NotBlank(message = "'street' is a mandatory field")
    private String state;

    @NotBlank(message = "'area' is a mandatory field")
    private String area;

    @NotBlank(message = "'city' is a mandatory field")
    private String city;

    @Digits(message = "pincode must be a 6-digit number", integer = 6, fraction =0)
    private String pincode;

    @Digits(message = "Phone number must be between 10-12 digits", integer = 12 , fraction = 0)
    private String phone;

//    private Long userId;
}
