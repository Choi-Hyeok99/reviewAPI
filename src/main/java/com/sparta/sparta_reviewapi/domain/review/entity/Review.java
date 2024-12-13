package com.sparta.sparta_reviewapi.domain.review.entity;

import com.sparta.sparta_reviewapi.domain.product.entity.Product;
import com.sparta.sparta_reviewapi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id", "user_id"})
})
@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Float score;

    @Column(nullable = false)
    private String content;

    private String imageUrl = "/image.png";

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Version
    private Integer version; // 낙관적 락을 위한 필드

    public Review(Product product, User user, Float score, String content,String imageUrl) {
        this.product = product;
        this.user = user;
        this.score = score;
        this.content = content;
        this.imageUrl = imageUrl;
    }
}
