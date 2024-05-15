package com.example.productservice.controllers;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.services.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {
    private  ProductService productService;
    public ProductController(ProductService productService)
    {
        this.productService=productService;
    }

    @GetMapping("/products")
    public String getAllProducts()
    {
        return "Getting all products";
    }
    @GetMapping("/products/{productId}")
    public  String getSingleProduct(@PathVariable("productId") Long productId)
    {
        return "Get single product with id" +" "+ productId;
    }
    @PostMapping("/products")
    public String addNewProduct(@RequestBody ProductDto productDto)
    {
        return "adding new product " + productDto;
    }
    @PutMapping("/products/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId)
    {
        return "updating product with id : " + productId;
    }
    @DeleteMapping("/products/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId)
    {
        return "deleting product with id : " + productId;
    }
}
