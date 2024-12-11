package com.sparta.sparta_reviewapi.product.repository;

import com.sparta.sparta_reviewapi.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    }

