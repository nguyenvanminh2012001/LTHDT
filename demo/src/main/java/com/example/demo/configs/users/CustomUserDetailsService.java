package com.example.demo.configs.users;

import com.example.demo.constants.UserTypeConstant;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.UserLoginEntity;
import com.example.demo.repositories.UserLoginRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final String USER = UserTypeConstant.USER;

    private final UserLoginRepository userLoginRepository;
    private final UserRepository userRepository;
//    private final ApplicantRepository applicantRepository;

    CustomUserDetailsService(UserRepository userRepository, UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
//        this.applicantRepository = applicantRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserLoginEntity userLoginEntity = userLoginRepository.findByUserName(username);
        if (userLoginEntity == null) {
            throw new UsernameNotFoundException(username);
        }
        UserEntity userEntity = userRepository.findUserById(userLoginEntity.getUserId());
        // Set user type for user principal
        userEntity.setUserName(userLoginEntity.getUserName());
        userEntity.setUserPassword(userLoginEntity.getUserPassword());

        return new MyUserDetails(userEntity);
    }
}
