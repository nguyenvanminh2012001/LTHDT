package com.example.demo.entities;

import javax.persistence.*;

@Entity
@Table(name = "product_image")
public class ProductImageEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String image1;
    private String image2;
    private String image3;
    private String image4;

    public ProductImageEntity() {
    }

    public ProductImageEntity(String image1, String image2, String image3, String image4) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

    public long getId() {
        return id;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getImage3() {
        return image3;
    }

    public String getImage4() {
        return image4;
    }


    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

}
