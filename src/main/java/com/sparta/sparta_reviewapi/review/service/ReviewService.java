package com.sparta.sparta_reviewapi.review.service;

import com.sparta.sparta_reviewapi.product.entity.Product;
import com.sparta.sparta_reviewapi.product.repository.ProductRepository;
import com.sparta.sparta_reviewapi.review.entity.Review;
import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.entity.ReviewResponseDto;
import com.sparta.sparta_reviewapi.review.repository.ReviewRepository;
import com.sparta.sparta_reviewapi.user.entity.User;
import com.sparta.sparta_reviewapi.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewResponseDto createReview(Long productId, ReviewRequestDto requestDto) {
        // 1. 상품 확인 (없으면 예외 발생)
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        // 2. 유저 확인 (없으면 예외 발생)
        User user = userRepository.findById(requestDto.getUserId())
                                  .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

        // 3. 리뷰 객체 생성
        Review review = new Review(product, user, requestDto.getScore(), requestDto.getContent());

        // 4. 리뷰 저장
        reviewRepository.save(review);

        // 5. 리뷰 카운트 증가
        product.setReviewCount(product.getReviewCount()+1);

        // 6. score 평균 계산
        float newScore = (product.getScore() * product.getReviewCount() + requestDto.getScore()) / (product.getReviewCount() + 1);
        product.setScore(newScore);

        // 7. 저장
        productRepository.save(product);
        productRepository.flush();


        // 8. ResponseDto 반환
        return new ReviewResponseDto(
                "리뷰가 성공적으로 등록되었습니다.",
                productId,
                requestDto.getUserId(),
                requestDto.getScore(),
                requestDto.getContent(),
                LocalDateTime.now()  // 리뷰가 생성된 현재 시간
        );
    }
}
