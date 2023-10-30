package com.example.demo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Table(name = "recommendProduct")
public class RecommendProductEntity {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity userEntity;

    @OneToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductCategoryEntity categoryEntity;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "category_id")
    private long categoryId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    public RecommendProductEntity(long userId, long categoryId) {
        this.userId = userId;
        this.categoryId = categoryId;
    }

    public RecommendProductEntity() {

    }
}
