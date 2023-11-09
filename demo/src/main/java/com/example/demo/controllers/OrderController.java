package com.example.demo.controllers;

import com.example.demo.business.logic.OrderImpl;
import com.example.demo.business.validators.CartValidator;
import com.example.demo.exceptions.CartEntityNotExistException;
import com.example.demo.models.OrderModel;
import com.example.demo.models.UserInfoModel;
import com.example.demo.models.UserResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private OrderImpl orderImpl;
    private CartValidator cartValidator;

    @PostMapping("/{user_id}")
    public ResponseEntity<UserResponseModel> order(
            @PathVariable("user_id") long userId,
            @RequestBody UserInfoModel orderModel
    ) throws CartEntityNotExistException {
        cartValidator.checkCartExist(userId);
        orderImpl.order(userId, orderModel);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Order successfully!"),
                HttpStatus.OK
        );
    }

    @GetMapping("/{user_id}")
    public List<OrderModel> getOrder(@PathVariable("user_id") long userId) {
        return orderImpl.getOrder(userId);
    }
}