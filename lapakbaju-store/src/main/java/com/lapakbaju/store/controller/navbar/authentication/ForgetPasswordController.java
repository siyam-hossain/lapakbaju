package com.lapakbaju.store.controller.navbar.authentication;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@AllArgsConstructor

public class ForgetPasswordController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/reset-password")
    public String resetPasword(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword
    ){
        if (!password.equals(confirmPassword)){
            return "redirect:/forget-password?error=true";
        }

        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()){
            return "redirect:/forget-password?error=true";
        }

        UserEntity user = userOptional.get();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "redirect:/forget-password?success";
    }

}
