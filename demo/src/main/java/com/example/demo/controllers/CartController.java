package com.example.demo.controllers;


import com.example.demo.business.logic.CartImpl;
import com.example.demo.business.validators.CartValidator;
import com.example.demo.business.validators.ProductValidator;
import com.example.demo.business.validators.UserValidator;
import com.example.demo.exceptions.CartEntityNotExistException;
import com.example.demo.models.ProductCartModel;
import com.example.demo.models.UserResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {
    private CartImpl cartImpl;
    private CartValidator cartValidator;
    private ProductValidator productValidator;
    private UserValidator userValidator;

    @PostMapping("/{user_id}")
    public ResponseEntity<UserResponseModel> addToCart(
            @PathVariable("user_id") long userId,
            @RequestParam("product_id") long productId
    ) {
        cartImpl.addToCart(userId, productId);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Add this product to cart successfully!"),
                HttpStatus.OK
        );
    }

    @GetMapping("/{user_id}")
    public List<ProductCartModel> getCart(
            @PathVariable("user_id") long userId
    ) throws CartEntityNotExistException {
        cartValidator.checkCartExist(userId);

        return cartImpl.getCart(userId);
    }

    @DeleteMapping ("/{user_id}")
    public ResponseEntity<UserResponseModel> deleteProductInCart(
            @PathVariable("user_id") long userId,
            @RequestParam("product_id") long productId
    )
//            throws ProductNotExistException, UserNotExistException
    {
//        productValidator.checkProductExist(productId);
//        userValidator.checkUserExist(userId);
        cartImpl.deleteProductInCart(userId, productId);
        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Delete this product successfully!"),
                HttpStatus.OK
        );
    }

    @PutMapping ("/{user_id}")
    public ResponseEntity<UserResponseModel> updateQuantityInCart(
            @PathVariable("user_id") long userId,
            @RequestParam("product_id") long productId,
            @RequestParam("feature") String feature
    )
    {
        if(Objects.equals(feature, "inc")) {
            cartImpl.incQuantityInCart(userId, productId);
            return new ResponseEntity<>(
                    new UserResponseModel(HttpStatus.OK.value(), "Inc this product's quantity successfully!"),
                    HttpStatus.OK
            );
        }
        else {
            cartImpl.desQuantityInCart(userId, productId);
            return new ResponseEntity<>(
                    new UserResponseModel(HttpStatus.OK.value(), "Des this product's quantity successfully!"),
                    HttpStatus.OK
            );
        }

    }
}
