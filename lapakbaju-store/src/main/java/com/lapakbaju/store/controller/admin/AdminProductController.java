package com.lapakbaju.store.controller.admin;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @GetMapping("/products")
    public String listProducts(Principal principal, Model model) {
        if (principal != null) {
            UserEntity user = userRepository.findByEmail(principal.getName())
                    .orElse(null);
            model.addAttribute("user", user);
        }

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("activePage", "list-products");

        return "admin/products-list";
    }
}