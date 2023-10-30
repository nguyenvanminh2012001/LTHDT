package com.example.demo.business.validators;

import com.example.demo.exceptions.ProductNotExistException;
import com.example.demo.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductValidator {
    private ProductRepository productRepository;

    public void checkProductExist(Long id) throws ProductNotExistException {
        productRepository.findById(id);
    }

}
