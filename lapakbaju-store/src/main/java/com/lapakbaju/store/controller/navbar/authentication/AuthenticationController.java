package com.lapakbaju.store.controller.navbar.authentication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/login")
    public String login() {
        return "login_fragments/login-form";
    }

    @GetMapping("/register")
    public String register() {
        return "login_fragments/registration-form";
    }

    @GetMapping("/forget-password")
    public String forgetPassword(){
        return "login_fragments/forget-password";
    }
}
