package com.lapakbaju.store.controller.user_profile;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.user_profile.UserOrderEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.repository.user_profile.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserProfileController {

    private final UserRepository userRepository;
    private final UserOrderRepository userOrderRepository;

    @GetMapping("/user-profile")
    public String userProfile(Authentication authentication, Model model) {

        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserOrderEntity> orders = userOrderRepository.findByUserId(user.getId());
        user.setOrders(orders);

        model.addAttribute("user", user);
        return "user_profile_fragment/user-profile";
    }
}