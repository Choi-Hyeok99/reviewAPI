package com.sparta.sparta_reviewapi.domain.product.entity;

import com.sparta.sparta_reviewapi.domain.product.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Bean
    CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                Product product1 = new Product();
                product1.setReviewCount(0L);
                product1.setScore(0.0f);
                productRepository.save(product1);

                Product product2 = new Product();
                product2.setReviewCount(0L);
                product2.setScore(0.0f);
                productRepository.save(product2);

                System.out.println("더미 상품 데이터 초기화 완료");
            } else {
                System.out.println("이미 데이터가 존재합니다.");
            }
        };
    }
}
