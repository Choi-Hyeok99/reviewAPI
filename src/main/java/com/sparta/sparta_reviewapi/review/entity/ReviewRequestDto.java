package com.sparta.sparta_reviewapi.review.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ReviewRequestDto {

    private Long userId;      // 작성자 ID
    private float score;      // 리뷰 점수 (1-5점)
    private String content;   // 리뷰 내용
    private MultipartFile image;  // MultipartFile 필드

}
