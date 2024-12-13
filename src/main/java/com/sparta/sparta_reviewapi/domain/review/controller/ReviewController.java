package com.sparta.sparta_reviewapi.domain.review.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.sparta_reviewapi.common.exception.InvalidDataException;
import com.sparta.sparta_reviewapi.domain.review.dto.ReviewListResponseDto;
import com.sparta.sparta_reviewapi.domain.review.dto.ReviewRequestDto;
import com.sparta.sparta_reviewapi.domain.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@AllArgsConstructor
@RequestMapping("/products/{productId}/reviews") // 상품별 리뷰 API
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> createReview(@PathVariable Long productId,
                                             @RequestPart("image") MultipartFile image,
                                             @RequestPart("review") String reviewJson) {
        // JSON 문자열을 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        ReviewRequestDto requestDto;
        try {
            requestDto = objectMapper.readValue(reviewJson, ReviewRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new InvalidDataException("리뷰 JSON 데이터 형식이 잘못되었습니다.");
        }

        reviewService.createReview(productId, requestDto, image);
        return ResponseEntity.noContent().build();
    }

    // 리뷰 조회
    @GetMapping
    public ReviewListResponseDto getReviews(@PathVariable Long productId,
                                            @RequestParam(defaultValue = "0") Long cursor, // 커서 값
                                            @RequestParam(defaultValue = "10") int size) {

        return reviewService.getReviews(productId, cursor, size);
    }
}
