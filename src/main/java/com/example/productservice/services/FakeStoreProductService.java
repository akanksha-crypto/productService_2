package com.example.productservice.services;

import com.example.productservice.dto.FakeStoreProductDto;
import com.example.productservice.dto.ProductDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder)
    {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto[]> l =restTemplate.getForEntity("https://fakestoreapi.com/products",
                ProductDto[].class );
        List<Product> answer = new ArrayList<>();
        for(ProductDto productDto : l.getBody())
        {
           /* Product product = new Product();
            product.setId(productDto.getId());
            product.setTitle(productDto.getTitle());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setImageUrl(productDto.getImage());
            Category category =new Category();
            category.setName(productDto.getCategory());
            product.setCategory(category);
            answer.add(product);*/

            answer.add(productDto.toProduct());
        }
        return answer;
    }

    @Override
    public Product getSingleProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",
                FakeStoreProductDto.class, productId);
       /* ProductDto productDto = response.getBody();
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImage());
        Category category =new Category();
        category.setName(productDto.getCategory());
        product.setCategory(category);*/

        if(response == null)
        {
            throw new ProductNotFoundException("Product with id" + productId + "not found" +
                    "Try a product with id less than 21");
        }
        return response.getBody().toProduct1();
        //(url, returntype(converting json which we receive to the returntype), paramaters in url)
    }

    @Override
    public Product addNewProduct(ProductDto product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<ProductDto> response = restTemplate.postForEntity("https://fakestoreapi.com/products",
                product, ProductDto.class);

        ProductDto productDto = response.getBody();
        Product product1 = new Product();
        product1.setId(productDto.getId());
        product1.setTitle(productDto.getTitle());
        product1.setPrice(productDto.getPrice());
        product1.setDescription(productDto.getDescription());
        product1.setImageUrl(productDto.getImage());
        Category category =new Category();
        category.setName(productDto.getCategory());
        product1.setCategory(category);

        return product1;
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //restTemplate.p
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

//        HttpEntity<FakeStoreProductDto> requestEntity = new HttpEntity<>(fakeStoreProductDto);
//        ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.exchange(
//                "https://fakestoreapi.com/products/{productId}",
//                HttpMethod.PATCH,
//                requestEntity,
//                FakeStoreProductDto.class,
//                productId
//        );
        FakeStoreProductDto response = fakeStoreProductDto;
        response.setId(productId);
        return response.toProduct1();
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange("https://fakestoreapi.com/products/{id}",
                HttpMethod.DELETE, null, FakeStoreProductDto.class);
        return  response.getBody().toProduct1();
    }
}
