package com.example.demo.controllers;


import com.example.demo.business.logic.ProductImpl;
import com.example.demo.business.validators.ProductValidator;
import com.example.demo.exceptions.ProductNotExistException;
import com.example.demo.models.ProductModel;
import com.example.demo.models.UserResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/product")
public class ProductController {
    private ProductImpl productImpl;
    private ProductValidator productValidator;

    @Autowired
    public ProductController(ProductImpl productImpl, ProductValidator productValidator) {
        this.productImpl = productImpl;
        this.productValidator = productValidator;
    }
    

    @DeleteMapping("{product_id}")
    public ResponseEntity<UserResponseModel> deleteProductById(@PathVariable("product_id") long productId) throws ProductNotExistException {
        productValidator.checkProductExist(productId);

        productImpl.deleteProductById(productId);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Delete product successfully!"),
                HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<UserResponseModel> createProduct(@RequestBody ProductModel productModel) {

        productImpl.createProduct(productModel);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Create product successfully!"),
                HttpStatus.OK
        );
    }

}
