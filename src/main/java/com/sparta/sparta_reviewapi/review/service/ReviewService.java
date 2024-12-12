package com.sparta.sparta_reviewapi.review.service;

import com.sparta.sparta_reviewapi.product.entity.Product;
import com.sparta.sparta_reviewapi.product.repository.ProductRepository;
import com.sparta.sparta_reviewapi.review.entity.Review;
import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.entity.ReviewResponseDto;
import com.sparta.sparta_reviewapi.review.repository.ReviewRepository;
import com.sparta.sparta_reviewapi.user.entity.User;
import com.sparta.sparta_reviewapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewResponseDto createReview(Long productId, ReviewRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        User user = userRepository.findById(requestDto.getUserId())
                                  .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Review review = new Review(product, user, requestDto.getScore(), requestDto.getContent());
        reviewRepository.save(review);

        // 리뷰 통계 업데이트
        long newReviewCount = product.getReviewCount() + 1;
        float newScore = (product.getScore() * product.getReviewCount() + requestDto.getScore()) / newReviewCount;
        product.updateReview(newReviewCount, newScore);
        productRepository.save(product);

        log.info("리뷰 생성 완료: productId={}, reviewId={}, newReviewCount={}, newScore={}",
                productId, review.getId(), newReviewCount, newScore);

        return new ReviewResponseDto(
                "리뷰가 성공적으로 등록되었습니다.",
                productId,
                requestDto.getUserId(),
                requestDto.getScore(),
                requestDto.getContent(),
                LocalDateTime.now()
        );
    }
}
