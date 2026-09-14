package com.lapakbaju.store.controller.navbar.authentication;

import com.lapakbaju.store.entity.authentication.UserEntity;
import com.lapakbaju.store.repository.authentication.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class RegisterController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public String createUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword) {

        // Check password
        if (!password.equals(confirmPassword)) {
            return "redirect:/register?error=passwordMismatch";
        }

        // Check duplicate email
        if (userRepository.findByEmail(email).isPresent()) {
            return "redirect:/register?error=emailExists";
        }

        UserEntity user = new UserEntity();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // New users are always USER
        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login?registered=true";
    }
}