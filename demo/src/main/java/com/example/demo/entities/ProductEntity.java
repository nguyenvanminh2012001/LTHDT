package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@Table(name = "product")
public class ProductEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private Double price;

    @Column(name="buying_price")
    private Double buyingPrice;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "image_id")
    private long imageId;

    @Column(name = "inventory_id")
    private long inventoryId;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductCategoryEntity productCategoryEntity;

    @OneToOne
    @JoinColumn(name = "image_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductImageEntity productImageEntity;

    @OneToOne
    @JoinColumn(name = "inventory_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductInventoryEntity productInventory;

}
