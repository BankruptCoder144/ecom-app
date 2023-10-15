package com.example.productservice.repo;

import com.example.productservice.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductDetails, Integer> {
}
