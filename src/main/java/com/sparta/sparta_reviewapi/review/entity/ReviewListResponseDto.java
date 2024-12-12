package com.sparta.sparta_reviewapi.review.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Data
public class ReviewListResponseDto {
    private Long totalCount; // 총 리뷰 수
    private Double score;    // 평균 점수
    private Long cursor;       // 다음 페이지 커서 값 (마지막 리뷰의 ID)
    private List<ReviewResponseDto> reviews; // 리뷰 목록

    // 생성자 추가
    public ReviewListResponseDto(Long totalCount, Double score, Long cursor, List<ReviewResponseDto> reviews) {
        this.totalCount = totalCount;
        this.score = new BigDecimal(score).setScale(1, RoundingMode.HALF_UP).doubleValue();
        this.cursor = cursor;
        this.reviews = reviews;
    }
}
