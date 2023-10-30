package com.example.demo.models;

import com.example.demo.entities.ProductImageEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductModel {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Double buyingPrice;
    private Double price;
    private ProductImageEntity productImageEntity;

    public ProductModel() {

    }
}