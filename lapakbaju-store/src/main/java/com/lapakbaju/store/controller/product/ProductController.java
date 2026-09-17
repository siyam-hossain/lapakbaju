package com.lapakbaju.store.controller.product;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.repository.product.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Data
@AllArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @GetMapping("/{id}")
    public String getProductDetails(
            @PathVariable("id") Long id,
            Principal principal,
            Model model
    ){
        if (principal != null) {
            String email = principal.getName();
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new RuntimeException("User not found"));
            model.addAttribute("user", user);
        }


        Product product =  productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        return "product_fragments/product-details";
    }


}
