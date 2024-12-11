package com.sparta.sparta_reviewapi.review.entity;

import com.sparta.sparta_reviewapi.product.entity.Product;
import com.sparta.sparta_reviewapi.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user; // 리뷰 작성자


    private int score; // 리뷰 점수

    private String content; // 리뷰 내용
    private String imageUrl; // 이미지 URL

}
