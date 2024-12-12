package com.sparta.sparta_reviewapi.domain.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReviewRequestDto {

    private Long productId;  // 상품 ID
    private Long userId;      // 작성자 ID
    @Min(value = 1, message = "Score must be at least 1")
    @Max(value = 5, message = "Score must not exceed 5")
    private float score;     // 리뷰 점수 (1-5점)
    private String content;   // 리뷰 내용
    private MultipartFile image;  // MultipartFile 필드

}
