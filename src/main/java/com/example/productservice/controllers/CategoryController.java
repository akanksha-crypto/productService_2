package com.example.productservice.controllers;

import com.example.productservice.services.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/categories")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService)
    {
        this.categoryService=categoryService;
    }

    @GetMapping()
    public String getAllCategories()
    {
        return "Getting all categories";
    }
    @GetMapping("/{categoryId}")
    public String getProductsInCategory(@PathVariable("categoryId") Long categoryId)
    {
        return "Getting products in category";
    }
}
