package com.example.demo.repositories;

import com.example.demo.entities.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    @Query("select id from ProductCategoryEntity where name= :categoryName")
    Optional<Long> findIdByCategoryName(String categoryName);

}
