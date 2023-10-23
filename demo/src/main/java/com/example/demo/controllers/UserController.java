package com.example.demo.controllers;

import com.example.demo.business.logic.UserRegisterBusinessImpl;
import com.example.demo.business.validators.UserValidator;
import com.example.demo.exceptions.SystemException;
import com.example.demo.models.UserRegisterModel;
import com.example.demo.models.UserResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final UserRegisterBusinessImpl userRegisterBusinessImpl;
    private final UserValidator userValidator;

    @PostMapping("/register")
    public ResponseEntity<UserResponseModel> register(@RequestBody @Valid UserRegisterModel userRegisterModel) throws SystemException {
        userValidator.checkDuplicateUsername(userRegisterModel.getUserName());

        userRegisterBusinessImpl.register(userRegisterModel);

        return new ResponseEntity<>(
                new UserResponseModel(HttpStatus.OK.value(), "Register successfully!"),
                HttpStatus.OK
        );
    }



}
