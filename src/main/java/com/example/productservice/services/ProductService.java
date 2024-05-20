package com.example.productservice.services;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getSingleProduct(Long productId);

    Product addNewProduct(ProductDto product);

    Product updateProduct(Long productId, Product product);

    boolean deleteProduct(Long productId);
}
