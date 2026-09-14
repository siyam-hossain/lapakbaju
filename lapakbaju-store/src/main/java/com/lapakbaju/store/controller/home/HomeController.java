package com.lapakbaju.store.controller.home;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import com.lapakbaju.store.service.authentication.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;



@Controller
@AllArgsConstructor
public class HomeController {
    private final UserRepository userRepository;


    @GetMapping("/")
    String base(Principal principal, Model model) {

        if (principal != null) {
            String email = principal.getName();
            UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow(()-> new RuntimeException("User not found"));
            model.addAttribute("user", user);
        }

        return "index";
    }
}
