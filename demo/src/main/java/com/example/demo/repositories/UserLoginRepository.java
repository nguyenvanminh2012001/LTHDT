package com.example.demo.repositories;


import com.example.demo.entities.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLoginEntity, Long> {
    UserLoginEntity findByUserName(String userName);
}
