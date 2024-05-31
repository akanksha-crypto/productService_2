package com.example.productservice.services;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {

    public List<Product> getAllProducts();

    public Product getSingleProduct(Long productId) throws ProductNotFoundException;

    public Product addNewProduct(ProductDto product);

    public Product updateProduct(Long productId, Product product)throws ProductNotFoundException;

    Product deleteProduct(Long productId) throws ProductNotFoundException;
}
