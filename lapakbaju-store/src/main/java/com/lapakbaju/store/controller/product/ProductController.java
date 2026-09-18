package com.lapakbaju.store.controller.product;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final UserRepository userRepository;
    private final ProductService productService;

    /**
     * Endpoint GET: /products
     * Displays product list with Min/Max Price filtering only.
     */
    @GetMapping
    public String getAllProducts(
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            Principal principal,
            Model model
    ) {
        if (principal != null) {
            String email = principal.getName();
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("user", user);
        }

        // Retrieve filtered list by price range
        model.addAttribute("products", productService.getFilteredProducts(minPrice, maxPrice));

        // Retain input states
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "product_fragments/products";
    }

    /**
     * Endpoint GET: /products/{id}
     * Displays single product details page.
     */
    @GetMapping("/{id}")
    public String getProductDetails(
            @PathVariable("id") Long id,
            Principal principal,
            Model model
    ) {
        if (principal != null) {
            String email = principal.getName();
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("user", user);
        }

        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        return "product_fragments/product-details";
    }
}