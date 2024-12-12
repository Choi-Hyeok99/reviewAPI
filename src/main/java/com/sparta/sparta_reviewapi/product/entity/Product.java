package com.sparta.sparta_reviewapi.product.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reviewcount", nullable = false)
    private Long reviewCount = 0L; // 리뷰 총 개수
    @Column(nullable = false)
    private Float score = 0.0f; // 상품의 평균 점수


    public void updateReview(long newReviewCount, float newScore) {
        this.reviewCount = newReviewCount;
        this.score = newScore;
    }
}
