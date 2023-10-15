package com.example.productservice.repo;

import com.example.productservice.entity.CategoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryDetails, Integer> {
}
