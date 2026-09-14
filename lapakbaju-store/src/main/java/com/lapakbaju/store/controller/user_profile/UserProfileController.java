package com.lapakbaju.store.controller.user_profile;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.entity.user_profile.UserAddressEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.repository.user_profile.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserProfileController {


    private final UserRepository userRepository;


    @GetMapping("/user-profile")
    public String userProfile(Authentication authentication, Model model) {

        String email = authentication.getName();

        UserEntity user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("user", user);
        return "user_profile_fragment/user-profile";
    }
}
