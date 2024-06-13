package com.example.productservice;

import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.repositories.projection.ProductWithDescriptionAndPrice;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductServiceApplicationTests {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testJpaDeclaredJoin()
    {
        List<Product> products = productRepository.findByTitle("i-phone 14 pro");
        for(Product product : products)
        {
            System.out.println(product.getTitle());
            System.out.println(product.getPrice());
            System.out.println(product.getCategory());
        }
    }


    @Test
    void testProjections() {
        List<ProductWithDescriptionAndPrice> products = productRepository.someMethod1("new electronics");
        for (ProductWithDescriptionAndPrice product : products) {
            System.out.println(product.getPrice());
            System.out.println(product.getId());
        }
    }

    @Test
    void testHQL() {
        List<Product> products = productRepository.getProductWithCategoryName("new electronics");
        for (Product product : products) {
            System.out.println(product.getTitle());
            System.out.println(product.getCategory().getName());
            System.out.println(product.getPrice());
        }
    }

    @Test
    void testSpecificFields() {
        List<String> productTitles = productRepository.someTitleMethod("new electronics");
        for (String productTitle : productTitles) {
            System.out.println(productTitle);
        }
    }

    @Test
    @Transactional
    void testFetchType() {
        Optional<Category> category = categoryRepository.findById(10L);
        if(category.isPresent())
        {
            System.out.println(category.get().getName());
            List<Product> products = category.get().getProducts();
            for (Product product : products){
                System.out.println(product.getTitle());
            }
        }
    }

    @Test
    @Transactional
    void testFetchMode()
    {
        List<Category> cat = categoryRepository.findByNameEndingWith("new electronics");
        for(Category c : cat)
        {
            System.out.println(c.getName());
            List<Product> prods = c.getProducts();
            for (Product p : prods)
            {
                System.out.println(p.getTitle());
            }
        }
    }
}
