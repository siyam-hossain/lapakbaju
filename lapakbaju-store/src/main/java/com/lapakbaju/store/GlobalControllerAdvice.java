package com.lapakbaju.store;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.service.cart.CartService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
@AllArgsConstructor
public class GlobalControllerAdvice {

    private final CartService cartService;
    private final UserRepository userRepository;

    @ModelAttribute("cartItemCount")
    public int populateCartItemCount(Principal principal) {
        if (principal == null) {
            return 0;
        }

        UserEntity user = userRepository.findByEmail(principal.getName())
                .orElse(null);

        if (user == null) {
            return 0;
        }

        return cartService.getCartCount(user);
    }
}
