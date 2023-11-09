package com.example.demo.controllers;

import com.example.demo.business.logic.OrderImpl;
import com.example.demo.models.OrderModel;
import com.example.demo.models.UserResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/order")
@AllArgsConstructor
public class OrderAdminController {
    private OrderImpl orderImpl;

    @GetMapping()
    public List<OrderModel> getAllOrder() {
        return orderImpl.getAllOrder();
    }

    @PutMapping("/{order_id}")
    public ResponseEntity<UserResponseModel> acceptOrder(
            @PathVariable("order_id") long orderId
    ) {

        orderImpl.acceptOrder(orderId);
        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Accept this order successfully!"),
                HttpStatus.OK
        );
    }



}
