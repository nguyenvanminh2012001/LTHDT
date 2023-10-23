package com.example.demo.business.validators;

import com.example.demo.exceptions.DuplicateUsernameException;
import com.example.demo.exceptions.UserNotExistException;
import com.example.demo.repositories.UserLoginRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    private final UserLoginRepository userLoginRepository;
    private final UserRepository userRepository;

    public UserValidator(UserLoginRepository userLoginRepository, UserRepository userRepository) {
        this.userLoginRepository = userLoginRepository;
        this.userRepository = userRepository;
    }

    public void checkDuplicateUsername(String username) throws DuplicateUsernameException {
        if (userLoginRepository.findByUserName(username) != null) {
            throw new DuplicateUsernameException(username);
        }
    }


    public void checkUserExist(Long id) throws UserNotExistException {
        if(userRepository.findUserById(id) == null) {
            throw new UserNotExistException(id);
        }
    }
}
