package com.ecommerce.ProductService.DTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;


public class ProductDTO {

    @Size(min=2, message = "name should have atleast 2 character")
    private String name;


    @Size(min = 2, message = "description should have atleast 2 character")
    private String description;
    @Min(value = 1, message = "Price should be greater than 0")
    private Double price;

    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Min(value = 1, message = "quantity should be greater than or equal to 1")
    private Integer quantity;
//    @NotNull(message = "Subcategory ID cannot be null")

    private String subcategoryName;
//    @Min(value= 1, message = "subcategoryId should be greater than or equal to 1")
//    @Max(value = 5)
//    private Long subcategoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

//    public Long getSubcategoryId() {
//        return subcategoryId;
//    }
//
//    public void setSubcategoryId(Long subcategoryId) {
//        this.subcategoryId = subcategoryId;
//    }
}