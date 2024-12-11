package com.sparta.sparta_reviewapi.review.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewResponseDto {
    private String message;   // 성공 메시지
    private Long productId;   // 상품 ID
    private Long userId;      // 작성자 ID
    private float score;      // 리뷰 점수
    private String content;   // 리뷰 내용
    private LocalDateTime createdAt; // 생성일자

    public ReviewResponseDto(String message, Long productId, Long userId, float score, String content, LocalDateTime createdAt) {
        this.message = message;
        this.productId = productId;
        this.userId = userId;
        this.score = score;
        this.content = content;
        this.createdAt = createdAt;
    }
}
