package com.sparta.sparta_reviewapi.review.controller;

import com.sparta.sparta_reviewapi.review.entity.ReviewListResponseDto;
import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.entity.ReviewResponseDto;
import com.sparta.sparta_reviewapi.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
@RequestMapping("/products/{productId}/reviews") // 상품별 리뷰 API
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> createReview(@PathVariable Long productId,
                                             @Valid @RequestBody ReviewRequestDto requestDto) {
        reviewService.createReview(productId, requestDto);
        return ResponseEntity.noContent().build();
    }

    // 리뷰 조회
    @GetMapping
    public ReviewListResponseDto getReviews(@PathVariable Long productId,
                                            @RequestParam(defaultValue = "0")Long cursor, // 커서 값
                                            @RequestParam(defaultValue = "10")int size){

        return reviewService.getReviews(productId,cursor, size);
    }
}



