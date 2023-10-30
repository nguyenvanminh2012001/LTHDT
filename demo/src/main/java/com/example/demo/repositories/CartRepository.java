package com.example.demo.repositories;

import com.example.demo.entities.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> getCartEntityByUserIdAndProductId(long userId, long productId);

    Optional<List<CartEntity>> findAllByUserId(long userId);

    void deleteByUserIdAndProductId(long userId, long productId);

    void deleteCartEntityByUserId(long userId);

}
