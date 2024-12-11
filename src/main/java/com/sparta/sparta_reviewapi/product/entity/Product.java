package com.sparta.sparta_reviewapi.product.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reviewCount = 0L; // 리뷰 총 개수
    private Float score = 0.0f; // 상품의 평균 점수


}
