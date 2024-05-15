package com.example.productservice.services;

import org.springframework.stereotype.Service;

@Service
public class FakeStoreCategoryService implements CategoryService{
    @Override
    public String getAllCategories() {
        return "";
    }

    @Override
    public String getProductsInCategory(Long categoryId) {
        return "";
    }
}
