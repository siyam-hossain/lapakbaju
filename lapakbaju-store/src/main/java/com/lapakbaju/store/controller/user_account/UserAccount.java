package com.lapakbaju.store.controller.user_account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserAccount {
    @GetMapping("/user-account")
    public String userAccount(Model model) {
        model.addAttribute("userName","Siyam Hossain");
        model.addAttribute("email","s@gmail.com");

        return "user_account_fragment/user-account";
    }
}
