package com.sparta.sparta_reviewapi.review.service;

import com.sparta.sparta_reviewapi.product.entity.Product;
import com.sparta.sparta_reviewapi.product.repository.ProductRepository;
import com.sparta.sparta_reviewapi.review.entity.Review;
import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.repository.ReviewRepository;
import com.sparta.sparta_reviewapi.user.entity.User;
import com.sparta.sparta_reviewapi.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    // 리뷰 등록
    public String createReview(Long productId, ReviewRequestDto requestDto) {
        Optional<Product> productList = productRepository.findById(productId);
        Optional<User> userList= userRepository.findById(requestDto.getUserId());

        if (productList.isEmpty() || userList.isEmpty()) {
            throw new RuntimeException("Product or User not found!");
        }

        Product product = productList.get();
        User user = userList.get();

        // 리뷰 객체 생성
        Review review = new Review(product,user,requestDto.getScore(),requestDto.getContent());

        reviewRepository.save(review);

        return "Review Create successfully";
    }
}
