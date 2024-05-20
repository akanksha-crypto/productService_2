package com.example.productservice.controllers;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.dto.ProductRequestDto;
import com.example.productservice.dto.ProductResponseDto;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class ProductController {
    private  ProductService productService;
    public ProductController(ProductService productService)
    {
        this.productService=productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts()
    {
        return productService.getAllProducts();
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable("productId") Long productId)
    {
//        ProductResponseDto productResponseDto = new ProductResponseDto();
//        productResponseDto.setProduct(productService.getSingleProduct(productId));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
                headers.add("auth-token", "noaccess chiuchiuchapachapa");

        ResponseEntity<Product> response =new ResponseEntity<>(
                  productService.getSingleProduct(productId), headers,
                  HttpStatus.NOT_FOUND);
          return response;
    }
//    public ProductResponseDto getSingleProduct(@PathVariable("productId") Long productId)
//    {
//        ProductResponseDto productResponseDto = new ProductResponseDto();
//        productResponseDto.setProduct(productService.getSingleProduct(productId));
//
//        return productResponseDto;
//    }
    @PostMapping("/products")
    public ResponseEntity<Product> addNewProduct(@RequestBody ProductDto product)
    {
        Product newProduct = productService.addNewProduct(product);
        ResponseEntity<Product> response = new ResponseEntity<>(newProduct, HttpStatus.OK);
        return response;
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
