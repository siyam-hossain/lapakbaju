package com.lapakbaju.store.controller.admin;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.product.Product;
import com.lapakbaju.store.entity.user_profile.UserOrderEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.repository.product.ProductRepository;
import com.lapakbaju.store.repository.user_profile.UserOrderRepository;
import com.lapakbaju.store.repository.user_profile.UserOrderRepository.MonthlyRevenueProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminAnalyticsController {

    private final UserRepository userRepository;
    private final UserOrderRepository userOrderRepository;
    private final ProductRepository productRepository;

    @GetMapping("/analytics")
    public String showAnalytics(Principal principal, Model model) {
        if (principal != null) {
            UserEntity user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            model.addAttribute("user", user);
        }

        List<UserOrderEntity> allOrders = userOrderRepository.findAll();
        List<Product> allProducts = productRepository.findAll();

        // 1. Calculate Total Earning
        BigDecimal totalEarning = allOrders.stream()
                .map(order -> order.getTotalPrice() != null ? order.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 2. Total Number of Orders
        long totalOrders = allOrders.size();

        // 3. Dynamic Revenue Trend Data for Area Chart
        List<MonthlyRevenueProjection> trendData = userOrderRepository.findMonthlyRevenueTrend();

        List<String> revenueMonths = trendData.stream()
                .map(MonthlyRevenueProjection::getMonthName)
                .collect(Collectors.toList());

        List<BigDecimal> monthlyRevenues = trendData.stream()
                .map(MonthlyRevenueProjection::getTotalRevenue)
                .collect(Collectors.toList());

        // 4. Data for Product Frequency (Stock Quantity)
        List<String> productNames = allProducts.stream().map(Product::getName).collect(Collectors.toList());
        List<Integer> productFrequencies = allProducts.stream()
                .map(p -> p.getInventory() != null ? p.getInventory().getStockQuantity() : 0)
                .collect(Collectors.toList());

        // 5. Data for Product Market Cap (Price * Stock Quantity)
        List<BigDecimal> marketCaps = allProducts.stream()
                .map(p -> {
                    BigDecimal price = p.getPrice() != null ? p.getPrice() : BigDecimal.ZERO;
                    int stock = p.getInventory() != null ? p.getInventory().getStockQuantity() : 0;
                    return price.multiply(BigDecimal.valueOf(stock));
                })
                .collect(Collectors.toList());

        // Model Attributes
        model.addAttribute("totalEarning", totalEarning);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("revenueMonths", revenueMonths);
        model.addAttribute("monthlyRevenues", monthlyRevenues);
        model.addAttribute("productNames", productNames);
        model.addAttribute("productFrequencies", productFrequencies);
        model.addAttribute("marketCaps", marketCaps);

        return "admin/analytics";
    }
}