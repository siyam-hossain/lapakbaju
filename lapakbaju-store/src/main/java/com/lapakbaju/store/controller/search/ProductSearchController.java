package com.lapakbaju.store.controller.search;

import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductSearchController {

    private final ProductService productService;

    @GetMapping("/products/search")
    public String searchProducts(@RequestParam(name = "keyword", required = false, defaultValue = "") String keyword, Model model) {
        List<Product> products = productService.searchProducts(keyword);

        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalResults", products.size());

        return "search_product/search-results";
    }
}
