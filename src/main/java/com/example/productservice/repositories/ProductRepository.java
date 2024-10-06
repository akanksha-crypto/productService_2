package com.example.productservice.repositories;

import com.example.productservice.models.Product;
import com.example.productservice.repositories.projection.ProductWithDescriptionAndPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>{
    Product save(Product product);
    List<Product> findAll();
  //  Optional<Product> findById(Long productId);
    Optional<Product> findByIdIs(Long id);
    List<Product> findByTitle(String title);
    void deleteById(Long productId);

    @Query("select p from Product p where p.category.name = :categoryName")
    List<Product> getProductWithCategoryName(String categoryName);

    @Query("select p.title as title from Product p where p.category.name = :categoryName")
    List<String> someTitleMethod(String categoryName);

//    @Query("select p.id as id, p.price as price from Product p where p.category.name =: categoryName")
//    List<ProductWithDescriptionAndPrice> getPrice(String categoryName);

    @Query("select p.id as id, p.price as price from Product p where p.category.name= :categoryName")
    List<ProductWithDescriptionAndPrice> someMethod1(String categoryName);

}
