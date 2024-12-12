package com.sparta.sparta_reviewapi.review.entity;

import com.sparta.sparta_reviewapi.product.entity.Product;
import com.sparta.sparta_reviewapi.user.entity.User;
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

    public Review(Product product, User user, Float score, String content) {
        this.product = product;
        this.user = user;
        this.score = score;
        this.content = content;
        this.imageUrl = "/image.png";
    }
}
