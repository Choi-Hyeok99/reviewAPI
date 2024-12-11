package com.sparta.sparta_reviewapi.review.service;

import com.sparta.sparta_reviewapi.review.entity.ReviewRequestDto;
import com.sparta.sparta_reviewapi.review.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class ReviewService {


    public String createReview(Long productId, ReviewRequestDto requestDto) {
        return null;

    }
}
