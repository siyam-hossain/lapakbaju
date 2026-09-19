package com.lapakbaju.store.controller.contact;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("activePage", "contact");
        return "contact/contact";
    }

    @PostMapping("/contact/send")
    public String handleContactSubmit(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("subject") String subject,
            @RequestParam("message") String message,
            RedirectAttributes redirectAttributes) {

        // Handle feedback processing / email dispatch here
        redirectAttributes.addFlashAttribute("successMessage", "Thank you, " + name + "! Your message has been sent successfully.");
        return "redirect:/contact";
    }
}