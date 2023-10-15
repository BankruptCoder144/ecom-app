package com.example.productservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "Categories")
public class CategoryDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String description;

    @ManyToMany(mappedBy = "productCategories")
    List<ProductDetails> products;
}
