package com.sparta.sparta_reviewapi.review.service;

import com.sparta.sparta_reviewapi.product.entity.Product;
import com.sparta.sparta_reviewapi.product.repository.ProductRepository;
import com.sparta.sparta_reviewapi.review.entity.Review;
import com.sparta.sparta_reviewapi.review.entity.ReviewListResponseDto;
import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.entity.ReviewResponseDto;
import com.sparta.sparta_reviewapi.review.repository.ReviewRepository;
import com.sparta.sparta_reviewapi.user.entity.User;
import com.sparta.sparta_reviewapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public ReviewListResponseDto getReviews(Long productId, Pageable pageable) {
        // 상품 ID에 맞는 리뷰를 페이지네이션으로 조회
        Page<Review> reviewPage = reviewRepository.findByProductId(productId, pageable);

        // 리뷰 목록을 ReviewResponseDto로 변환
        List<ReviewResponseDto> reviews = reviewPage.getContent().stream()
                                                    .map(review -> new ReviewResponseDto(
                                                            "리뷰 조회 성공",
                                                            review.getProduct().getId(),
                                                            review.getUser().getId(),
                                                            review.getScore(),
                                                            review.getContent(),
                                                            review.getCreatedAt()
                                                    ))
                                                    .collect(Collectors.toList());

        // 총 리뷰 수와 평균 점수 계산
        long totalCount = reviewPage.getTotalElements();
        double avgScore = reviewPage.getContent().stream()
                                    .mapToDouble(Review::getScore)
                                    .average()
                                    .orElse(0.0);

        // ReviewListResponseDto
        return new ReviewListResponseDto(
                totalCount,                   // 총 리뷰 수
                avgScore,                     // 평균 점수
                pageable.getPageSize(),       // 페이지 크기
                pageable.getPageNumber(),     // 현재 페이지 번호
                reviews                       // 리뷰 목록
        );

    }
}