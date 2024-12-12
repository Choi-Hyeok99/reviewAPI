package com.sparta.sparta_reviewapi.review.repository;

import com.sparta.sparta_reviewapi.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    Page<Review> findByProductId(Long productId, Pageable pageable);
    Page<Review> findByProductIdAndId(Long productId, Long cursor, Pageable pageable);
}
