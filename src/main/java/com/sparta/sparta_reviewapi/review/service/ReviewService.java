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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<Void> createReview(Long productId, ReviewRequestDto requestDto, MultipartFile image) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));


        User user = getOrCreateUser(requestDto.getUserId());


        // 이미지 URL 저장
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = saveImage(image);
        }

        // Review 객체 생성
        Review review = new Review(product, user, requestDto.getScore(), requestDto.getContent(), imageUrl);
        reviewRepository.save(review);

        // 리뷰 통계 업데이트
        long newReviewCount = product.getReviewCount() + 1;
        float newScore = (product.getScore() * product.getReviewCount() + requestDto.getScore()) / newReviewCount;
        product.updateReview(newReviewCount, newScore);
        productRepository.save(product);

        return ResponseEntity.noContent().build();
    }


    public ReviewListResponseDto getReviews(Long productId, Long cursor,int size) {
        // 커서 기반으로 리뷰 조회
        Pageable pageable = PageRequest.of(0,size, Sort.by(Sort.Order.desc("id")));

        // 커서가 0이면 처음부터, 아니면 cursor보다 큰 리뷰 가져오기
        Page<Review> reviewPage;
        if (cursor == 0){
            reviewPage = reviewRepository.findByProductId(productId,pageable);
        } else {
            reviewPage = reviewRepository.findByProductIdAndId(productId,cursor,pageable);
        }

        // ReviewResponseDto 반환
        List<ReviewResponseDto> reviews = reviewPage.getContent().stream()
                                                    .map(review -> new ReviewResponseDto(
                                                            review.getId(),
                                                            review.getUser().getId(),
                                                            review.getScore(),
                                                            review.getContent(),
                                                            review.getImageUrl(),
                                                            review.getCreatedAt()
                                                    ))
                                                    .collect(Collectors.toList());

        // 총 리뷰 수와 평균 점수 계산
        long totalCount = reviewPage.getTotalElements();
        double avgScore = reviewPage.getContent().stream()
                                    .mapToDouble(Review::getScore)
                                    .average()
                                    .orElse(0.0);

        Long nextCursor = reviewPage.hasNext() ? reviewPage.getContent().get(reviewPage.getContent().size()-1).getId() : null;

        // ReviewListResponseDto
        return new ReviewListResponseDto(
                totalCount,                   // 총 리뷰 수
                avgScore,
                nextCursor,
                reviews                       // 리뷰 목록
        );
    }
    public String saveImage(MultipartFile image){
        // 파일 이름 생성
        String fileName = UUID.randomUUID()
                       .toString() + "_" + image.getOriginalFilename();
        String filePath = "src/main/resources/static/images" + fileName;

        File directory = new File("src/main/resources/static/images");
        if (!directory.exists()) {
            directory.mkdirs(); // 디렉토리 생성
        }

        try{
            Files.copy(image.getInputStream(), Paths.get(filePath));
        } catch (IOException e){
            throw new RuntimeException("이미지 저장에 실패했습니다.",e);
        }
        return "/images/" + fileName;
    }
    public User getOrCreateUser(Long userId) {
        return userRepository.findById(userId)
                             .orElseGet(() -> {
                                 User newUser = new User();
                                 return userRepository.save(newUser); // 데이터베이스에 저장
                             });
    }
}