package com.lapakbaju.store.controller.navbar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login_fragments/login-registration";
    }

}
