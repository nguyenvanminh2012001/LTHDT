package com.example.demo.business.logic;

import com.example.demo.entities.RecommendProductEntity;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.UserLoginEntity;
import com.example.demo.models.UserRegisterModel;
import com.example.demo.repositories.RecommendProductRepository;
import com.example.demo.repositories.UserLoginRepository;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class UserRegisterBusinessImpl {
    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final RecommendProductRepository recommendProductRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public void register(UserRegisterModel registerModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(registerModel.getFirstName());
        userEntity.setLastName(registerModel.getLastName());
        userEntity.setUserTypeId(1L); // user
         // Save new user
        UserEntity newUser = userRepository.save(userEntity);

        if(registerModel.getHat()) {
            RecommendProductEntity recommendProductEntity = new RecommendProductEntity();
            recommendProductEntity.setUserId(newUser.getId());
            recommendProductEntity.setCategoryId(1L);
            recommendProductRepository.save(recommendProductEntity);
        }

        if(registerModel.getPate()) {
            RecommendProductEntity recommendProductEntity = new RecommendProductEntity();
            recommendProductEntity.setUserId(newUser.getId());
            recommendProductEntity.setCategoryId(2L);
            recommendProductRepository.save(recommendProductEntity);
        }

        if(registerModel.getCat()) {
            RecommendProductEntity recommendProductEntity = new RecommendProductEntity();
            recommendProductEntity.setUserId(newUser.getId());
            recommendProductEntity.setCategoryId(3L);
            recommendProductRepository.save(recommendProductEntity);
        }

        if(registerModel.getQuanao()) {
            RecommendProductEntity recommendProductEntity = new RecommendProductEntity();
            recommendProductEntity.setUserId(newUser.getId());
            recommendProductEntity.setCategoryId(4L);
            recommendProductRepository.save(recommendProductEntity);
        }

        UserLoginEntity userLoginEntity = new UserLoginEntity();
        userLoginEntity.setUserId(newUser.getId());
        userLoginEntity.setUserName(registerModel.getUserName());
        userLoginEntity.setUserPassword(bCryptPasswordEncoder.encode(registerModel.getUserPassword()));

        // Save new user's login credentials
        userLoginRepository.save(userLoginEntity);

    }
}
