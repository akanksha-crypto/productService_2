package com.example.productservice.services;

import com.example.productservice.dto.ProductDto;
import com.example.productservice.exceptions.ProductNotFoundException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public SelfProductService(CategoryRepository categoryRepository, ProductRepository productRepository)
    {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException{
        Optional<Product> singleproduct = productRepository.findByIdIs(productId);
        if(singleproduct.isEmpty())
        {
            throw new ProductNotFoundException("Product with " + productId + " not found");
        }
        return singleproduct.get();
    }

    @Override
    public Product addNewProduct(ProductDto product) {
        Product newProduct = new Product();
        newProduct.setTitle(product.getTitle());
        newProduct.setDescription(product.getDescription());
        newProduct.setImageUrl(product.getImage());
        newProduct.setPrice(product.getPrice());

        String categoryInDb = product.getCategory();
        Category categoryInDb1 = categoryRepository.findByName(categoryInDb);
        if(categoryInDb1 == null)
        {
            Category newCategory = new Category();
            newCategory.setName(categoryInDb);
          //  categoryRepository.save(newCategory);
            categoryInDb1 = newCategory;
        }
        newProduct.setCategory(categoryInDb1);
        Product savedproduct = productRepository.save(newProduct);
        return savedproduct;
    }

    @Override
    public Product updateProduct(Long productId, Product product) throws ProductNotFoundException {
        Optional<Product> updatingProduct = productRepository.findByIdIs(productId);
        if(updatingProduct.isEmpty())
        {
            throw new ProductNotFoundException("Product with " + productId + " not found");
        }
        if(updatingProduct.get().getCategory() != null)
        {
            String newcategoryInDb = String.valueOf(product.getCategory());
            Category categoryInDb1 = categoryRepository.findByName(newcategoryInDb);
            if(categoryInDb1 == null)
            {
                Category newCategory = new Category();
                newCategory.setName(newcategoryInDb);
                //  categoryRepository.save(newCategory);
                categoryInDb1 = newCategory;
            }
            updatingProduct.get().setCategory(categoryInDb1);
        }
        if (updatingProduct.get().getDescription()!= null)
        {
            updatingProduct.get().setDescription(product.getDescription());
        }
        if(updatingProduct.get().getPrice() != 0)
        {
            updatingProduct.get().setPrice(product.getPrice());
        }
        if(updatingProduct.get().getTitle()!=null)
        {
            updatingProduct.get().setTitle(product.getTitle());
        }
        return productRepository.save(updatingProduct.get());
    }

    @Override
    public Product deleteProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findByIdIs(productId);
        if(product == null)
        {
            throw new ProductNotFoundException("Product with " + productId + " not found");
        }
        productRepository.deleteById(productId);
        return product.get();
    }
}
