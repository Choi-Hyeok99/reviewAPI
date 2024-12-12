package com.sparta.sparta_reviewapi.review.entity;

import lombok.Data;

import java.util.List;

@Data
public class ReviewListResponseDto {
    private Long totalCount; // 총 리뷰 수
    private Double score;    // 평균 점수
    private int pageSize;    // 페이지 크기
    private int currentPage; // 현재 페이지 번호
    private List<ReviewResponseDto> reviews; // 리뷰 목록

    // 생성자 추가
    public ReviewListResponseDto(Long totalCount, Double score, int pageSize, int currentPage, List<ReviewResponseDto> reviews) {
        this.totalCount = totalCount;
        this.score = score;
        this.pageSize = pageSize;
        this.currentPage = currentPage;
        this.reviews = reviews;
    }
}
