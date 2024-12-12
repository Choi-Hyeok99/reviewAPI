package com.sparta.sparta_reviewapi.review.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.sparta_reviewapi.review.entity.ReviewListResponseDto;
import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.service.ReviewService;
import jakarta.validation.Valid;
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
            throw new RuntimeException("Invalid JSON format for review", e);
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
