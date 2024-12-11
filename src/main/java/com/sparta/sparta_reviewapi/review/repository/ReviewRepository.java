package com.sparta.sparta_reviewapi.review.repository;

import com.sparta.sparta_reviewapi.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
}
