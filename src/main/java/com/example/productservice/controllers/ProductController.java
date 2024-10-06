package com.example.productservice.controllers;

import com.example.productservice.authcommons.AuthenticationCommons;
import com.example.productservice.dto.ProductDto;
import com.example.productservice.dto.ErrorDto;
import com.example.productservice.dto.ProductResponseDto;
import com.example.productservice.dto.UserDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private  ProductService productService;
    private ModelMapper modelMapper;
    private AuthenticationCommons authenticationCommons;

    public ProductController(@Qualifier("selfProductService") ProductService productService,
                             ModelMapper modelMapper,
                             AuthenticationCommons authenticationCommons)
    {
        this.productService=productService;
        this.modelMapper = modelMapper;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping("/products")
    public List<ProductResponseDto> getAllProducts()
    {
        List<Product> products = productService.getAllProducts();
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for(Product product : products)
        {
            productResponseDtos.add(convertToProductResponseDto(product));
        }
        return productResponseDtos;
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> getSingleProduct(@PathVariable("productId") Long productId,
                                                               @RequestHeader String authenticationtoken) {
//        ProductResponseDto productResponseDto = new ProductResponseDto();
//        productResponseDto.setProduct(productService.getSingleProduct(productId));
      //  MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
      //          headers.add("auth-token", "noaccess jhupijhupijhapajhapa");
        ResponseEntity<ProductResponseDto> response = null;
        UserDto userDto = authenticationCommons.validateToken(authenticationtoken);
        if (userDto == null){
            response = new ResponseEntity<>(null,
                    HttpStatus.UNAUTHORIZED);
            return response;
        }
        Product product1 = productService.getSingleProduct(productId);

        response =new ResponseEntity<>(
                  convertToProductResponseDto(product1),
                  HttpStatus.OK);
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
    public ResponseEntity<ProductResponseDto> addNewProduct(@RequestBody ProductDto product)
    {
        Product newProduct = productService.addNewProduct(product);
        ResponseEntity<ProductResponseDto> response = new ResponseEntity<>(convertToProductResponseDto(newProduct),
                HttpStatus.OK);
        return response;
    }
    @PatchMapping ("/products/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("productId") Long productId,
                                                            @RequestBody ProductDto productDto) throws ProductNotFoundException {
        Product product =new Product();
        product.setDescription(productDto.getDescription());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(productDto.getCategory());
        Product updatedProduct = productService.updateProduct(productId, product);
        ResponseEntity<ProductResponseDto> response =new ResponseEntity<>(convertToProductResponseDto(product),
                HttpStatus.NOT_FOUND);
        return response;
    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductResponseDto> deleteProduct(@PathVariable("productId") Long productId) throws ProductNotFoundException {
        Product deletedProduct = productService.deleteProduct(productId);
        ResponseEntity<ProductResponseDto> response = new ResponseEntity<>(
                convertToProductResponseDto(deletedProduct), HttpStatus.OK);
        return response;
    }

    public ProductResponseDto convertToProductResponseDto(Product product)
    {
        String categoryName = product.getCategory().getName();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setCategory(categoryName);
        return productResponseDto;
    }

    //Add Exception Handler
//    @ExceptionHandler(ProductNotFoundException.class)
//    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException)
//    {
//        ErrorDto errorDto = new ErrorDto();
//        errorDto.setMessage(productNotFoundException.getMessage());
//        return  new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
//    }
}
