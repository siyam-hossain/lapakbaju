package com.lapakbaju.store.repository.product;

import com.lapakbaju.store.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIsActiveTrue();
    List<Product> findByCategoryId(Long categoryId);
}
