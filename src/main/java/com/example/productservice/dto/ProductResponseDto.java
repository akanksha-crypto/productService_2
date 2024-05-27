package com.example.productservice.dto;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {

 //     private Product product;
    private Long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String imageUrl;
}
