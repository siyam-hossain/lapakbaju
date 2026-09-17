package com.lapakbaju.store.repository.product;

import com.lapakbaju.store.entity.product.ProductImage;
import com.lapakbaju.store.entity.product.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
