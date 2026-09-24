package com.lapakbaju.store.controller.admin;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.repository.product.ProductRepository;
import com.lapakbaju.store.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String listProducts(Principal principal, Model model) {
        if (principal != null) {
            UserEntity user = userRepository.findByEmail(principal.getName()).orElse(null);
            model.addAttribute("user", user);
        }

        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        model.addAttribute("activePage", "list-products");

        return "admin/products-list";
    }

    @GetMapping("/add")
    public String showAddProductForm(Principal principal, Model model) {
        if (principal != null) {
            UserEntity user = userRepository.findByEmail(principal.getName()).orElse(null);
            model.addAttribute("user", user);
        }

        model.addAttribute("product", new Product());
        model.addAttribute("activePage", "add-product");
        return "admin/add-product";
    }

    @PostMapping("/add")
    public String processAddProduct(
            @ModelAttribute("product") Product product,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "categoryIcon", defaultValue = "shirt") String categoryIcon,
            @RequestParam(value = "categoryBg", defaultValue = "#f3f4f6") String categoryBg,
            @RequestParam(value = "categoryColor", defaultValue = "#1f2937") String categoryColor,
            @RequestParam(value = "stockQuantity", defaultValue = "0") Integer stockQuantity,
            @RequestParam(value = "reorderThreshold", defaultValue = "10") Integer reorderThreshold,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes
    ) {
        try {
            productService.saveOrUpdateProduct(product, categoryName, categoryIcon, categoryBg, categoryColor, stockQuantity, reorderThreshold, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!");
            return "redirect:/admin/products";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add product: " + e.getMessage());
            return "redirect:/admin/products/add";
        }
    }

    /**
     * Endpoint GET: /admin/products/update/{id}
     * Shows update page populated with product data.
     */
    @GetMapping("/update/{id}")
    public String showUpdateProductForm(@PathVariable("id") Long id, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        if (principal != null) {
            UserEntity user = userRepository.findByEmail(principal.getName()).orElse(null);
            model.addAttribute("user", user);
        }

        Product product = productService.getProductById(id);
        if (product == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Product not found!");
            return "redirect:/admin/products";
        }

        model.addAttribute("product", product);
        model.addAttribute("activePage", "update-product");
        return "admin/update-product";
    }

    /**
     * Endpoint POST: /admin/products/update/{id}
     * Processes product updates.
     */
    @PostMapping("/update/{id}")
    public String processUpdateProduct(
            @PathVariable("id") Long id,
            @ModelAttribute("product") Product product,
            @RequestParam(value = "categoryName", required = false) String categoryName,
            @RequestParam(value = "categoryIcon", defaultValue = "shirt") String categoryIcon,
            @RequestParam(value = "categoryBg", defaultValue = "#f3f4f6") String categoryBg,
            @RequestParam(value = "categoryColor", defaultValue = "#1f2937") String categoryColor,
            @RequestParam(value = "stockQuantity", defaultValue = "0") Integer stockQuantity,
            @RequestParam(value = "reorderThreshold", defaultValue = "10") Integer reorderThreshold,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes
    ) {
        try {
            product.setId(id);
            productService.saveOrUpdateProduct(product, categoryName, categoryIcon, categoryBg, categoryColor, stockQuantity, reorderThreshold, imageFile);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
            return "redirect:/admin/products";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update product: " + e.getMessage());
            return "redirect:/admin/products/update/" + id;
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProductById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete product: " + e.getMessage());
        }
        return "redirect:/admin/products";
    }
}