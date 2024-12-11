package com.sparta.sparta_reviewapi.review.entity;

import com.sparta.sparta_reviewapi.product.entity.Product;
import com.sparta.sparta_reviewapi.user.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
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
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Long userId; // 리뷰 작성자

    @Column(nullable = false)
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score must not exceed 5")
    private float score; // 리뷰 점수 (1 ~ 5)

    @Column(nullable = false)
    private String content; // 리뷰 내용

    private String imageUrl = "/image.png"; //  더미 URL 설정

    @CreationTimestamp
    private LocalDateTime createdAt; // 생성 시간

    // 수정된 생성자
    public Review(Product product, Long userId, float score, String content) {
        validateScore(score);
        this.product = product;
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.imageUrl = "/image.png"; // 더미 URL로 설정
    }
    private void validateScore(float score) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }
    }
}
