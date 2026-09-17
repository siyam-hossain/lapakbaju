package com.lapakbaju.store.repository.product;

import com.lapakbaju.store.entity.product.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<ProductImage> findByIsMainTrue();
}
