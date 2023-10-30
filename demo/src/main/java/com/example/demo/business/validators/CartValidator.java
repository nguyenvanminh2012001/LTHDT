package com.example.demo.business.validators;

import com.example.demo.exceptions.CartEntityNotExistException;
import com.example.demo.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CartValidator {
    private CartRepository cartRepository;

    public void checkCartExist(Long userId) throws CartEntityNotExistException {
        if(!cartRepository.findAllByUserId(userId).isPresent()) {
            throw new CartEntityNotExistException(userId);
        }

    }
}
