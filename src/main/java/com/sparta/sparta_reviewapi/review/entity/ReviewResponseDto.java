package com.sparta.sparta_reviewapi.review.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewResponseDto {
    private Long productId;   // 상품 ID
    private Long userId;      // 작성자 ID
    private float score;      // 리뷰 점수
    private String content;   // 리뷰 내용
    private String imageUrl;  // 이미지 URL
    private LocalDateTime createdAt; // 생성일자

    // 생성자 추가
    public ReviewResponseDto(Long productId, Long userId, float score, String content, String imageUrl, LocalDateTime createdAt) {

        this.productId = productId;
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }
}

