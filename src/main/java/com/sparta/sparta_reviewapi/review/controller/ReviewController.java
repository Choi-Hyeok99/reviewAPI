package com.sparta.sparta_reviewapi.review.controller;

import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/products/{productId}/reviews") // 상품별 리뷰 API
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록
    @PostMapping
    public ResponseEntity<String> CreateReview(@PathVariable Long productId,
                                       @RequestBody ReviewRequestDto requestDto){
       reviewService.createReview(productId,requestDto);
        return ResponseEntity.ok("ok");
    }

    // 리뷰 조회
}
