package com.sparta.sparta_reviewapi.review.controller;

import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.entity.ReviewResponseDto;
import com.sparta.sparta_reviewapi.review.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/products/{productId}/reviews") // 상품별 리뷰 API
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 등록
    @PostMapping
    public ReviewResponseDto createReview(@PathVariable("productId") Long productId,
                                          @ModelAttribute ReviewRequestDto requestDto){

        return reviewService.createReview(productId,requestDto);
    }

    // 리뷰 조회
}
