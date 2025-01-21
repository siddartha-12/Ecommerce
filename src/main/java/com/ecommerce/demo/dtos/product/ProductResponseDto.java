package com.ecommerce.demo.dtos.product;

import com.ecommerce.demo.Entities.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor

@Getter
@Setter
public class ProductResponseDto {
    public Long id;
    public String title;
    public double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String description;
    public String image;
    public String category;

    public ProductResponseDto(Long id, String title, double price, String description, String image, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    static
    public ProductResponseDto createProductResponseDto(Product product){
        return new ProductResponseDto(product.getId(),product.getTitle(),product.getPrice(),
                product.getDescription(),product.getImage(),product.getCategory());

    }
}
