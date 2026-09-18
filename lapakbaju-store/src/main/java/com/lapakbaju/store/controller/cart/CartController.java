package com.lapakbaju.store.controller.cart;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.cart.CartItemEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.service.cart.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String viewCart(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        UserEntity user = getUser(principal);
        List<CartItemEntity> cartItems = cartService.getCartByUser(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("user", user);

        return "cart/cart-page";
    }

    @PostMapping("/add")
    public String addToCart(
            Principal principal,
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        UserEntity user = getUser(principal);
        cartService.addToCart(user, productId, quantity);

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(
            Principal principal,
            @RequestParam Long cartItemId,
            @RequestParam int quantity
    ) {
        if (principal == null) {
            return "redirect:/login";
        }

        UserEntity user = getUser(principal);
        cartService.updateQuantity(user, cartItemId, quantity);

        return "redirect:/cart";
    }

    @PostMapping("/checkout/success")
    public String completePayment(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        UserEntity user = getUser(principal);
        cartService.processCheckout(user);

        return "cart/payment-success";
    }

    private UserEntity getUser(Principal principal) {
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new IllegalStateException("Logged-in user not found"));
    }
}