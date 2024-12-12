package com.sparta.sparta_reviewapi.review.entity;

import lombok.Data;

import java.util.List;

@Data
public class ReviewListResponseDto {
    private Long totalCount; // 총 리뷰 수
    private Double score;
    private Long cursor; // 다음 페이지 커서 값
    private List<ReviewResponseDto> reviews; // 리뷰 목록
}
