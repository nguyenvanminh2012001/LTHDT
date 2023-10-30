package com.example.demo.controllers;


import com.example.demo.business.logic.ProductImpl;
import com.example.demo.business.validators.ProductValidator;
import com.example.demo.exceptions.ProductNotExistException;
import com.example.demo.models.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user/product")
public class ProductDisplayController {
    private ProductImpl productImpl;
    private ProductValidator productValidator;

    @Autowired
    public ProductDisplayController(ProductImpl productImpl, ProductValidator productValidator) {
        this.productImpl = productImpl;
        this.productValidator = productValidator;
    }

    @GetMapping(path = "/{product_id}")
    public ProductModel getProductById(@PathVariable("product_id") long productId) throws ProductNotExistException {
        productValidator.checkProductExist(productId);

        return productImpl.getProductById(productId);
    }

    @GetMapping("/related/{product_id}")
    public List<ProductModel> getRelatedProduct(@PathVariable("product_id") long productId) throws ProductNotExistException {
        productValidator.checkProductExist(productId);

        return productImpl.getProductRelated(productId);
    }

    @GetMapping()
    public List<ProductModel> getAllProduct() {
        return productImpl.getAllProduct();
    }

    @GetMapping("/recommend/{user_id}")
    public List<ProductModel> getProductRecommend(@PathVariable("user_id") Long userId) {
        return productImpl.getProductRecommend(userId);
    }

    @GetMapping("/search")
    public List<ProductModel> getProductSearch(
            @RequestParam("keyword") String keyword
    ) {
        return productImpl.getAllProductSearch(keyword);
    }

    @GetMapping("/filter")
    public List<ProductModel> getProductSearchCategory(
            @RequestParam("category_id") Long categoryId
    ) {
        return productImpl.getAllProductByCategory(categoryId);
    }

}
