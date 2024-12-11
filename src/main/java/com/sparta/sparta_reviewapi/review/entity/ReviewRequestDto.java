package com.sparta.sparta_reviewapi.review.entity;

import lombok.Data;

@Data
public class ReviewRequestDto {

    private Long userId;
    private Long productId;
    private Float score;
    private String content;

}
