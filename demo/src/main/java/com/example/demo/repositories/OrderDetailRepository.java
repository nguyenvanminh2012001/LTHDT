package com.example.demo.repositories;

import com.example.demo.entities.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Long> {
    List<OrderDetailEntity> findAllByOrderId(Long orderId);
}
