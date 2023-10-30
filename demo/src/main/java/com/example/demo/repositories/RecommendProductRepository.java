package com.example.demo.repositories;

import com.example.demo.entities.RecommendProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendProductRepository extends JpaRepository<RecommendProductEntity, Long> {
    @Query("SELECT categoryId FROM RecommendProductEntity WHERE userId= :userId")
    List<Long> findAllCategoryIdByUserId(long userId);

}
