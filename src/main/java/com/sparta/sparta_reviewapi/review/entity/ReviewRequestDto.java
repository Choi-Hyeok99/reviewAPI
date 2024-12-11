package com.sparta.sparta_reviewapi.review.entity;

import lombok.Data;

@Data
public class ReviewRequestDto {

    private Long userId;
    private Long productId;
    private int score;
    private String content;

}
