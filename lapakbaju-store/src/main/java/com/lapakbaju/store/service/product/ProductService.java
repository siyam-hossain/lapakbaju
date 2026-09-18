package com.lapakbaju.store.service.product;

import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getFilteredProducts(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}