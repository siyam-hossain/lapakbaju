package com.lapakbaju.store.service.product;

import com.lapakbaju.store.entity.product.*;
import com.lapakbaju.store.repository.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Value("${file.upload.product:D:/xampp/htdocs/lapakbaju/images/products/}")
    private String productUploadDir;

    public List<Product> getFilteredProducts(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public Product saveProduct(Product product, String categoryName, String categoryIcon, String categoryBg, String categoryColor, Integer stockQuantity, Integer reorderThreshold, MultipartFile imageFile) throws IOException {
        // Set Defaults for Booleans
        if (product.getIsHot() == null) product.setIsHot(false);
        if (product.getIsNew() == null) product.setIsNew(false);
        if (product.getIsFeatured() == null) product.setIsFeatured(false);
        if (product.getIsActive() == null) product.setIsActive(true);

        // Bind or Create Category Manually with mandatory entity fields
        if (StringUtils.hasText(categoryName)) {
            String trimmedName = categoryName.trim();
            Category category = categoryRepository.findByNameIgnoreCase(trimmedName)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setName(trimmedName);
                        newCategory.setIcon(StringUtils.hasText(categoryIcon) ? categoryIcon : "default-icon");
                        newCategory.setBg(StringUtils.hasText(categoryBg) ? categoryBg : "#ffffff");
                        newCategory.setColor(StringUtils.hasText(categoryColor) ? categoryColor : "#000000");
                        newCategory.setDefault_active(false);
                        return categoryRepository.save(newCategory);
                    });
            product.setCategory(category);
        }

        // Bind Inventory
        Inventory inventory = new Inventory();
        inventory.setStockQuantity(stockQuantity != null ? stockQuantity : 0);
        inventory.setRecorderThreshold(reorderThreshold != null ? reorderThreshold : 10);
        product.setInventory(inventory);

        // Handle Image Upload
        if (imageFile != null && !imageFile.isEmpty()) {
            String filename = uploadImage(imageFile);
            ProductImage productImage = new ProductImage();
            productImage.setImageUrl(filename);
            productImage.setIsMain(true);
            product.setImage(productImage);
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    private String uploadImage(MultipartFile file) throws IOException {
        File dir = new File(productUploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : ".jpg";
        String newFilename = UUID.randomUUID().toString() + ext;

        File serverFile = new File(dir, newFilename);
        file.transferTo(serverFile);

        return newFilename;
    }
}